package com.Booking.Booking.System.repository;

import com.Booking.Booking.System.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByStaffIdAndAppointmentTimeBetween(
            Long staffId, LocalDateTime startTime, LocalDateTime endTime);

    List<Appointment> findByUserId(Long userId);

    List<Appointment> findByStatus(String status);
}
