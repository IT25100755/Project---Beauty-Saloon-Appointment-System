package com.Booking.Booking.System.controller;

import com.Booking.Booking.System.model.Appointment;
import com.Booking.Booking.System.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    //  BOOK Appointment POST /api/appointments/book
    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody Appointment appointment) {
        try {
            Appointment booked = appointmentService.bookAppointment(appointment);
            return new ResponseEntity<>(booked, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Returns 400 Bad Request with the validation error message
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //  READ All Appointment GET /api/appointments
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    //  READ Appointment by ID GET /api/appointments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        return appointment.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
    }

    //  READ Appointments by User ID GET /api/appointments/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByUserId(userId));
    }

    //  READ Appointments by Status GET /api/appointments/status/{status}
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByStatus(status));
    }

    //  RESCHEDULE Appointment PUT /api/appointments/{id}/reschedule?newTime=2025-06-20T10:00:00
    @PutMapping("/{id}/reschedule")
    public ResponseEntity<?> rescheduleAppointment(@PathVariable Long id,
                                                      @RequestParam String newTime) {
        try {
            LocalDateTime time = LocalDateTime.parse(newTime);
            Appointment rescheduled = appointmentService.rescheduleAppointment(id, time);
            if (rescheduled == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(rescheduled);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //  CANCEL Appointment PUT /api/appointments/{id}/cancel
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        Appointment cancelled = appointmentService.cancelAppointment(id);
        if (cancelled == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cancelled);
    }

    //  COMPLETE Appointment PUT /api/appointments/{id}/complete
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeAppointment(@PathVariable Long id) {
        Appointment completed = appointmentService.completeAppointment(id);
        if (completed == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(completed);
    }

    //  DELETE Appointment DELETE /api/appointments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("Appointment with ID " + id + " has been deleted.");
    }
}
