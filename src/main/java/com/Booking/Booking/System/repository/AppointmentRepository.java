package com.Booking.Booking.System.repository;

import com.Booking.Booking.System.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Appointment.
 * Contains custom query methods for business logic.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // OLD: Check exact time slot — kept for backward compat but no longer used for booking
    List<Appointment> findByStaffIdAndAppointmentTimeBetween(
            Long staffId, LocalDateTime startTime, LocalDateTime endTime);

    // NEW: Get all active (non-cancelled) appointments for a staff member
    // Used for proper duration-aware overlap checking
    List<Appointment> findByStaffIdAndStatusNotIn(Long staffId, List<String> statuses);

    // Custom: Find all appointments for a specific user
    List<Appointment> findByUserId(Long userId);

    // Custom: Filter appointments by status (e.g., "Booked", "Cancelled", "Completed")
    List<Appointment> findByStatus(String status);
}
