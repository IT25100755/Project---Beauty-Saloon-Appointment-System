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

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private SalonServiceRepository salonServiceRepository;

    //  BOOK Appointment (CREATE with validation) 
    public Appointment bookAppointment(Appointment appointment) {

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
    //  READ 
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

    //  RESCHEDULE
    public Appointment rescheduleAppointment(Long id, LocalDateTime newTime) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setAppointmentTime(newTime);
            return bookAppointment(appointment);
        }
        return null;
    }

    //  CANCEL
    public Appointment cancelAppointment(Long id) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus("Cancelled");
            return appointmentRepository.save(appointment);
        }
        return null;
    }
    public Appointment completeAppointment(Long id) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus("Completed");
            return appointmentRepository.save(appointment);
        }
        return null;
    }

    //  DELETE 
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
