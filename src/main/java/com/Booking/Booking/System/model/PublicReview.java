package com.Booking.Booking.System.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "public_reviews")
public class PublicReview extends Review {

    //  Extra field specific to PublicReview
    @Column(nullable = false)
    private String displayName;

    // Constructors 
    public PublicReview() {
        super(); // Call the parent constructor
    }

    public PublicReview(int rating, String comment, LocalDateTime createdAt,
                        User user, SalonService salonService, Appointment appointment,
                        String displayName) {
        super(rating, comment, createdAt, user, salonService, appointment);
        this.displayName = displayName;
    }

    //  Getters & Setters 
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
}
