package com.Booking.Booking.System.service;

import com.Booking.Booking.System.model.Appointment;
import com.Booking.Booking.System.model.Offer;
import com.Booking.Booking.System.model.SalonService;
import com.Booking.Booking.System.repository.AppointmentRepository;
import com.Booking.Booking.System.repository.OfferRepository;
import com.Booking.Booking.System.repository.SalonServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Appointment booking business logic.
 * Contains validation: no past bookings, no staff double-booking.
 * Also applies offer discounts to final booking price (Issue Fix #1).
 * OOP Concept: Encapsulation of business rules.
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private SalonServiceRepository salonServiceRepository;

    // Injected to look up active offers for a service
    @Autowired
    private OfferRepository offerRepository;

    // ─── BOOK Appointment (CREATE with validation) ────────────────────────────────
    public Appointment bookAppointment(Appointment appointment) {

        // Rule 1: Cannot book in the past
        if (appointment.getAppointmentTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot book an appointment in the past.");
        }

        // Fetch the full service details from the database (NOT from frontend)
        SalonService service = salonServiceRepository
                .findById(appointment.getSalonService().getId())
                .orElseThrow(() -> new IllegalArgumentException("Salon Service not found."));

        // Validate service has a duration set
        int durationMins = service.getDurationMinutes();
        if (durationMins <= 0) {
            throw new IllegalArgumentException(
                    "Service '" + service.getName() + "' has no duration set. Please contact admin.");
        }

        // Calculate the NEW appointment time window
        LocalDateTime newStart = appointment.getAppointmentTime();
        LocalDateTime newEnd   = newStart.plusMinutes(durationMins);

        // Rule 2: Duration-aware overlap check
        // Statuses to BLOCK against: Booked, Confirmed, Pending
        // Statuses to IGNORE: Cancelled, Completed
        List<String> ignoredStatuses = List.of("Cancelled", "Completed");
        List<Appointment> activeAppointments = appointmentRepository
                .findByStaffIdAndStatusNotIn(appointment.getStaff().getId(), ignoredStatuses);

        for (Appointment existing : activeAppointments) {
            // Get the existing appointment's service duration
            SalonService existingService = salonServiceRepository
                    .findById(existing.getSalonService().getId())
                    .orElse(null);

            if (existingService == null) continue; // skip if service was deleted

            LocalDateTime existingStart = existing.getAppointmentTime();
            LocalDateTime existingEnd   = existingStart.plusMinutes(
                    existingService.getDurationMinutes() > 0
                    ? existingService.getDurationMinutes() : 60); // safe fallback

            // Overlap formula: newStart < existingEnd AND newEnd > existingStart
            // This correctly catches ALL overlap cases:
            //   - Booking INSIDE existing slot
            //   - Booking that SPANS existing slot
            //   - Booking at same start time
            boolean overlaps = newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);

            if (overlaps) {
                throw new IllegalArgumentException(
                        "Selected staff is already booked from "
                        + existingStart.toLocalTime()
                        + " to " + existingEnd.toLocalTime()
                        + ". Please choose another time.");
            }
        }

        // ── Offer / Discount Calculation (Issue Fix #1) ───────────────────────────
        // Step 1: Get original price from the database (ignore any frontend price)
        double originalPrice  = service.getPrice();
        double discountAmount = 0.0;
        double finalPrice     = originalPrice;
        Long   appliedOfferId    = null;
        String appliedOfferTitle = null;

        // Step 2: Look for any offer linked to this service
        List<Offer> offers = offerRepository.findBySalonServiceId(service.getId());

        if (!offers.isEmpty()) {
            // Use the first matching offer (there could be multiple; take the first)
            Offer bestOffer = offers.get(0);

            // Calculate discounted price using percentage
            double discount = originalPrice * bestOffer.getDiscountPercentage() / 100.0;
            double discounted = originalPrice - discount;

            // Final price must never go below 0
            if (discounted < 0) discounted = 0.0;

            discountAmount   = discount;
            finalPrice       = discounted;
            appliedOfferId   = bestOffer.getId();
            appliedOfferTitle = bestOffer.getTitle()
                    + " (" + bestOffer.getDiscountPercentage() + "% OFF)";
        }

        // Step 3: Set the calculated price fields on the appointment
        appointment.setOriginalPrice(originalPrice);
        appointment.setDiscountAmount(discountAmount);
        appointment.setFinalPrice(finalPrice);
        appointment.setAppliedOfferId(appliedOfferId);
        appointment.setAppliedOfferTitle(appliedOfferTitle);

        // Default status for a new booking
        appointment.setStatus("Booked");

        return appointmentRepository.save(appointment);
    }

    // ─── READ ────────────────────────────────────────────────────────────────────
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> getAppointmentsByUserId(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }

    // ─── RESCHEDULE (UPDATE appointmentTime) ──────────────────────────────────────
    public Appointment rescheduleAppointment(Long id, LocalDateTime newTime) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setAppointmentTime(newTime);
            // Re-run full booking validation (and re-apply offers) for the new time slot
            return bookAppointment(appointment);
        }
        return null;
    }

    // ─── CANCEL (UPDATE status to "Cancelled") ────────────────────────────────────
    public Appointment cancelAppointment(Long id) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus("Cancelled");
            return appointmentRepository.save(appointment);
        }
        return null;
    }

    // ─── COMPLETE (UPDATE status to "Completed") ──────────────────────────────────
    public Appointment completeAppointment(Long id) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus("Completed");
            return appointmentRepository.save(appointment);
        }
        return null;
    }

    // ─── CONFIRM (UPDATE status to "Confirmed" after successful payment) ──────────
    public Appointment confirmAppointment(Long id) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus("Confirmed");
            return appointmentRepository.save(appointment);
        }
        return null;
    }


    // ─── DELETE ──────────────────────────────────────────────────────────────────
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
