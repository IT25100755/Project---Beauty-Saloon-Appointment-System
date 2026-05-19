package com.Booking.Booking.System.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Appointment class — maps Users, SalonServices, and Staff together.
 * OOP Concepts: Association (has-a relationships via @ManyToOne).
 * Now also stores pricing information including offer discounts.
 */
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many appointments can belong to one User
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Many appointments can be made for one SalonService
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", nullable = false)
    private SalonService salonService;

    // Many appointments can be assigned to one Staff member
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Column(nullable = false)
    private LocalDateTime appointmentTime; // Date + time of appointment

    @Column(nullable = false)
    private String status; // "Booked", "Completed", "Cancelled"

    // ─── Pricing Fields (Issue Fix #1) ───────────────────────────────────────────
    // Original price from the service (before any discount)
    @Column(nullable = false)
    private double originalPrice;

    // How much was discounted (0 if no offer applied)
    @Column(nullable = false)
    private double discountAmount;

    // Final price customer pays = originalPrice - discountAmount
    @Column(nullable = false)
    private double finalPrice;

    // ID of the Offer that was applied (null if no offer)
    @Column
    private Long appliedOfferId;

    // Name of the applied offer (for display — stored so it survives offer deletion)
    @Column
    private String appliedOfferTitle;

    // ─── Constructors ────────────────────────────────────────────────────────────
    public Appointment() {}

    public Appointment(User user, SalonService salonService, Staff staff,
                       LocalDateTime appointmentTime, String status) {
        this.user = user;
        this.salonService = salonService;
        this.staff = staff;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    // ─── Getters & Setters ───────────────────────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public SalonService getSalonService() { return salonService; }
    public void setSalonService(SalonService salonService) { this.salonService = salonService; }

    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) { this.staff = staff; }

    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // ─── Price Getters & Setters ─────────────────────────────────────────────────
    public double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(double originalPrice) { this.originalPrice = originalPrice; }

    public double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }

    public double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(double finalPrice) { this.finalPrice = finalPrice; }

    public Long getAppliedOfferId() { return appliedOfferId; }
    public void setAppliedOfferId(Long appliedOfferId) { this.appliedOfferId = appliedOfferId; }

    public String getAppliedOfferTitle() { return appliedOfferTitle; }
    public void setAppliedOfferTitle(String appliedOfferTitle) { this.appliedOfferTitle = appliedOfferTitle; }
}
