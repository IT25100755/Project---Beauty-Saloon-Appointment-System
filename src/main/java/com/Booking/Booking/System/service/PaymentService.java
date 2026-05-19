package com.Booking.Booking.System.service;

import com.Booking.Booking.System.model.Appointment;
import com.Booking.Booking.System.model.Payment;
import com.Booking.Booking.System.model.Receipt;
import com.Booking.Booking.System.repository.AppointmentRepository;
import com.Booking.Booking.System.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for mock payment gateway.
 * OOP Concept: Abstraction — all payment business logic is here,
 *              controllers only call these methods.
 * OOP Concept: Encapsulation — private fields, public methods only.
 *
 * DEMO NOTE: No real card processing. Card number is NEVER saved.
 *            A simple validation accepts only "4242 4242 4242 4242" for CARD payments.
 */
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ReceiptService receiptService;

    /**
     * Step 1: Create a PENDING payment for the given appointment.
     * Amount is taken from the appointment's finalPrice — NEVER from the frontend.
     *
     * @param appointmentId  the appointment to pay for
     * @param paymentMethod  "CARD" or "CASH"
     * @return the saved Payment with status PENDING
     */
    public Payment createPayment(Long appointmentId, String paymentMethod) {
        // Fetch the full appointment from DB (price is trusted from backend only)
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + appointmentId));

        // Validate payment method
        if (!"CARD".equalsIgnoreCase(paymentMethod) && !"CASH".equalsIgnoreCase(paymentMethod)) {
            throw new IllegalArgumentException("Invalid payment method. Use CARD or CASH.");
        }

        // Use the appointment's backend-calculated final price
        double amount = appointment.getFinalPrice();

        Payment payment = new Payment(
                amount,
                paymentMethod.toUpperCase(),
                "PENDING",
                LocalDateTime.now(),
                appointment
        );

        return paymentRepository.save(payment);
    }

    /**
     * Step 2: Confirm the payment (mock validation).
     * For CARD: validates demo card number "4242424242424242" (digits only).
     * For CASH: directly marks as SUCCESS.
     * On SUCCESS: appointment status → "Confirmed", receipt is generated.
     *
     * @param paymentId  the payment to confirm
     * @param cardNumber raw card number entered by user (digits only, never saved)
     * @return the updated Payment with status SUCCESS or FAILED
     */
    public Payment confirmPayment(Long paymentId, String cardNumber) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found with ID: " + paymentId));

        if ("CARD".equals(payment.getPaymentMethod())) {
            // Strip spaces/dashes for comparison — NEVER persist the card number
            String digitsOnly = (cardNumber == null) ? "" : cardNumber.replaceAll("[\\s-]", "");
            boolean isValidDemoCard = "4242424242424242".equals(digitsOnly);

            if (!isValidDemoCard) {
                payment.setPaymentStatus("FAILED");
                paymentRepository.save(payment);
                throw new IllegalArgumentException(
                        "Invalid demo card number. Please use: 4242 4242 4242 4242");
            }
        }
        // CASH payments are always accepted immediately

        // Mark payment as SUCCESS
        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());
        payment = paymentRepository.save(payment);

        // Update appointment status to "Confirmed"
        Appointment appt = payment.getAppointment();
        appt.setStatus("Confirmed");
        appointmentRepository.save(appt);

        // Generate receipt
        receiptService.createReceipt(payment);

        return payment;
    }

    // ─── READ operations ─────────────────────────────────────────────────────

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getPaymentsByAppointmentId(Long appointmentId) {
        return paymentRepository.findByAppointmentId(appointmentId);
    }

    public Optional<Payment> getLatestPaymentForAppointment(Long appointmentId) {
        return paymentRepository.findFirstByAppointmentIdOrderByPaymentDateDesc(appointmentId);
    }
}
