package com.Booking.Booking.System.repository;

import com.Booking.Booking.System.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Payment entity.
 * Spring Data JPA provides all CRUD automatically.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find all payments for a specific appointment
    List<Payment> findByAppointmentId(Long appointmentId);

    // Find the latest payment for an appointment (useful for status check)
    Optional<Payment> findFirstByAppointmentIdOrderByPaymentDateDesc(Long appointmentId);
}
