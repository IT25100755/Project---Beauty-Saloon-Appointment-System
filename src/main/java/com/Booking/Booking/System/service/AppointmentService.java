package com.Booking.Booking.System.service;

import com.Booking.Booking.System.model.Appointment;
import com.Booking.Booking.System.model.SalonService;
import com.Booking.Booking.System.repository.AppointmentRepository;
import com.Booking.Booking.System.repository.SalonServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Appointment booking business logic.
 * Contains validation: no past bookings, no staff double-booking.
 * OOP Concept: Encapsulation of business rules.
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private SalonServiceRepository salonServiceRepository;

    // ─── BOOK Appointment (CREATE with validation) ────────────────────────────────
    public Appointment bookAppointment(Appointment appointment) {

        // Rule 1: Cannot book in the past
        if (appointment.getAppointmentTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot book an appointment in the past.");
        }

        // Fetch the service details to know its duration
        SalonService service = salonServiceRepository
                .findById(appointment.getSalonService().getId())
                .orElseThrow(() -> new IllegalArgumentException("Salon Service not found."));

        // Calculate the time window for this appointment
        LocalDateTime startTime = appointment.getAppointmentTime();
        LocalDateTime endTime = startTime.plusMinutes(service.getDurationMinutes());

        // Rule 2: Check if the staff member is already booked in this time slot
        List<Appointment> overlapping = appointmentRepository
                .findByStaffIdAndAppointmentTimeBetween(
                        appointment.getStaff().getId(),
                        startTime.minusMinutes(1),
                        endTime);

        if (!overlapping.isEmpty()) {
            throw new IllegalArgumentException(
                    "The selected staff member is already booked during this time slot.");
        }

        // Default status for a new booking
        appointment.setStatus("Booked");
        return appointmentRepository.save(appointment);
    }
}
