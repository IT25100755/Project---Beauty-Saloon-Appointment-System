package com.Booking.Booking.System.repository;

import com.Booking.Booking.System.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Receipt entity.
 * Spring Data JPA provides all CRUD automatically.
 */
@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    // Find receipt by the linked payment ID
    Optional<Receipt> findByPaymentId(Long paymentId);

    // Find receipt by its unique receipt number
    Optional<Receipt> findByReceiptNumber(String receiptNumber);
}
