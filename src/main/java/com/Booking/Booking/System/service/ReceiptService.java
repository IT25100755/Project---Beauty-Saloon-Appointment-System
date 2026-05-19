package com.Booking.Booking.System.service;

import com.Booking.Booking.System.model.Payment;
import com.Booking.Booking.System.model.Receipt;
import com.Booking.Booking.System.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for Receipt generation.
 * OOP Concept: Encapsulation — receipt generation logic is fully encapsulated here.
 * OOP Concept: Abstraction — controllers call createReceipt() without knowing internals.
 */
@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    /**
     * Generates a new receipt for a successful payment.
     * Receipt number format: RCP-XXXXXX (first 6 chars of UUID in uppercase).
     *
     * @param payment  the payment that was just marked SUCCESS
     * @return the saved Receipt
     */
    public Receipt createReceipt(Payment payment) {
        // Generate a unique, human-readable receipt number
        String receiptNumber = "RCP-" + UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 6)
                .toUpperCase();

        Receipt receipt = new Receipt(
                receiptNumber,
                LocalDateTime.now(),
                payment.getAmount(),
                payment,
                payment.getAppointment().getId()
        );

        return receiptRepository.save(receipt);
    }

    /**
     * Find receipt by the linked payment ID.
     */
    public Optional<Receipt> getReceiptByPaymentId(Long paymentId) {
        return receiptRepository.findByPaymentId(paymentId);
    }

    /**
     * Find receipt by its unique receipt number.
     */
    public Optional<Receipt> getReceiptByNumber(String receiptNumber) {
        return receiptRepository.findByReceiptNumber(receiptNumber);
    }

    /**
     * Find receipt by its own ID.
     */
    public Optional<Receipt> getReceiptById(Long id) {
        return receiptRepository.findById(id);
    }
}
