package com.Booking.Booking.System.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Payment entity for mock payment gateway.
 * OOP Concepts:
 *   - Encapsulation: private fields with getters/setters
 *   - Association: Payment has-a Appointment (ManyToOne)
 *
 * Payment statuses: PENDING, SUCCESS, FAILED, CANCELLED
 * NOTE: This is a DEMO/MOCK payment — no real card data is stored.
 */
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Amount taken directly from the appointment's finalPrice (backend-controlled)
    @Column(nullable = false)
    private double amount;

    // "CARD" or "CASH"
    @Column(nullable = false)
    private String paymentMethod;

    // PENDING | SUCCESS | FAILED | CANCELLED
    @Column(nullable = false)
    private String paymentStatus;

    // Timestamp when payment was processed
    @Column
    private LocalDateTime paymentDate;

    // Every payment belongs to one appointment
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    // ─── Constructors ─────────────────────────────────────────────────────────
    public Payment() {}

    public Payment(double amount, String paymentMethod, String paymentStatus,
                   LocalDateTime paymentDate, Appointment appointment) {
        this.amount        = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentDate   = paymentDate;
        this.appointment   = appointment;
    }

    // ─── Getters & Setters (Encapsulation) ───────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }
}
