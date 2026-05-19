package com.Booking.Booking.System.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Receipt entity generated after a successful payment.
 * OOP Concepts:
 *   - Encapsulation: private fields with getters/setters
 *   - Association: Receipt has-a Payment (OneToOne)
 */
@Entity
@Table(name = "receipts")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Human-readable receipt number, e.g. "RCP-A1B2C3"
    @Column(nullable = false, unique = true)
    private String receiptNumber;

    // When this receipt was issued
    @Column(nullable = false)
    private LocalDateTime issuedDate;

    // Total amount paid (copied from payment for quick display)
    @Column(nullable = false)
    private double totalAmount;

    // Every receipt links to exactly one payment
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    // Appointment ID stored for easy display (avoids deep nesting)
    @Column
    private Long appointmentId;

    // ─── Constructors ─────────────────────────────────────────────────────────
    public Receipt() {}

    public Receipt(String receiptNumber, LocalDateTime issuedDate,
                   double totalAmount, Payment payment, Long appointmentId) {
        this.receiptNumber = receiptNumber;
        this.issuedDate    = issuedDate;
        this.totalAmount   = totalAmount;
        this.payment       = payment;
        this.appointmentId = appointmentId;
    }

    // ─── Getters & Setters (Encapsulation) ───────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String receiptNumber) { this.receiptNumber = receiptNumber; }

    public LocalDateTime getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDateTime issuedDate) { this.issuedDate = issuedDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }

    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
}
