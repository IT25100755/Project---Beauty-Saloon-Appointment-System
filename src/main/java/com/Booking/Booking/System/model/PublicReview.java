package com.Booking.Booking.System.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * PublicReview — a subclass of Review for reviews visible to all visitors.
 *
 * OOP Concept: Inheritance — extends Review and adds a "displayName" field.
 * The displayName is the name shown publicly (e.g., "Anonymous" or a nickname).
 *
 * JPA: Uses InheritanceType.JOINED, so JPA creates a separate "public_reviews" table
 *      that holds only the extra column (display_name) and joins with the "reviews" table.
 */
@Entity
@Table(name = "public_reviews")
public class PublicReview extends Review {

    // ─── Extra field specific to PublicReview ────────────────────────────────────
    @Column(nullable = false)
    private String displayName; // The name shown publicly for this review (e.g., "Anonymous")

    // ─── Constructors ────────────────────────────────────────────────────────────
    public PublicReview() {
        super(); // Call the parent constructor
    }

    public PublicReview(int rating, String comment, LocalDateTime createdAt,
                        User user, SalonService salonService, Appointment appointment,
                        String displayName) {
        // Reuse the parent constructor to set shared fields
        super(rating, comment, createdAt, user, salonService, appointment);
        this.displayName = displayName;
    }

    // ─── Getters & Setters (Encapsulation) ───────────────────────────────────────
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
}
