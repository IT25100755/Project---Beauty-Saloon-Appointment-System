package com.Booking.Booking.System.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Review entity — stores user feedback for a salon service or appointment.
 *
 * OOP Concepts Applied:
 *  - Encapsulation   : All fields are private; accessed via public getters/setters.
 *  - Inheritance     : Serves as the base class for PublicReview and VerifiedReview
 *                      (using JPA InheritanceType.JOINED so each subclass gets its own table).
 *  - Association     : ManyToOne relationships link a Review to a User and a SalonService.
 */
@Entity
@Table(name = "reviews")
@Inheritance(strategy = InheritanceType.JOINED) // Each subclass (PublicReview, VerifiedReview) gets its own joined table
public class Review {

    // ─── Primary Key ─────────────────────────────────────────────────────────────
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    // ─── Rating field (1 to 5 stars) ─────────────────────────────────────────────
    @Column(nullable = false)
    private int rating; // Must be between 1 and 5 (validated in the service layer)

    // ─── Comment left by the user ─────────────────────────────────────────────────
    @Column(nullable = false, length = 1000)
    private String comment; // Cannot be empty (validated in the service layer)

    // ─── Timestamp: when the review was created ───────────────────────────────────
    @Column(nullable = false)
    private LocalDateTime createdAt; // Automatically set when a review is submitted

    // ─── Relationship: Many reviews → One User ────────────────────────────────────
    // A user can leave many reviews, but each review belongs to exactly one user
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column in "reviews" table
    private User user;

    // ─── Relationship: Many reviews → One SalonService ───────────────────────────
    // A service can receive many reviews, but each review targets one service
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", nullable = false) // Foreign key column in "reviews" table
    private SalonService salonService;

    // ─── Optional Relationship: Many reviews → One Appointment ───────────────────
    // A review may optionally be linked to a specific appointment (can be null)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id", nullable = true) // nullable = true means it is optional
    private Appointment appointment;

    // ─── Constructors ────────────────────────────────────────────────────────────
    public Review() {}

    public Review(int rating, String comment, LocalDateTime createdAt,
                  User user, SalonService salonService, Appointment appointment) {
        this.rating      = rating;
        this.comment     = comment;
        this.createdAt   = createdAt;
        this.user        = user;
        this.salonService = salonService;
        this.appointment = appointment;
    }

    // ─── Getters & Setters (Encapsulation) ───────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public SalonService getSalonService() { return salonService; }
    public void setSalonService(SalonService salonService) { this.salonService = salonService; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }
}
