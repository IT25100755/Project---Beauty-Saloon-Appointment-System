package com.Booking.Booking.System.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * VerifiedReview — a subclass of Review for reviews from confirmed appointment holders.
 *
 * OOP Concept: Inheritance — extends Review and adds an "appointmentConfirmed" flag.
 * A VerifiedReview is only given to users who actually completed an appointment,
 * making it more trustworthy than a regular review.
 *
 * JPA: Uses InheritanceType.JOINED — a separate "verified_reviews" table is created
 *      for the extra column (appointment_confirmed) and joined with "reviews".
 */
@Entity
@Table(name = "verified_reviews")
public class VerifiedReview extends Review {

    // ─── Extra field specific to VerifiedReview ───────────────────────────────────
    @Column(nullable = false)
    private boolean appointmentConfirmed; // True only if the user completed a real appointment

    // ─── Constructors ────────────────────────────────────────────────────────────
    public VerifiedReview() {
        super(); // Call the parent constructor
    }

    public VerifiedReview(int rating, String comment, LocalDateTime createdAt,
                          User user, SalonService salonService, Appointment appointment,
                          boolean appointmentConfirmed) {
        // Reuse parent constructor for shared fields
        super(rating, comment, createdAt, user, salonService, appointment);
        this.appointmentConfirmed = appointmentConfirmed;
    }

    // ─── Getters & Setters (Encapsulation) ───────────────────────────────────────
    public boolean isAppointmentConfirmed() { return appointmentConfirmed; }
    public void setAppointmentConfirmed(boolean appointmentConfirmed) {
        this.appointmentConfirmed = appointmentConfirmed;
    }
}
