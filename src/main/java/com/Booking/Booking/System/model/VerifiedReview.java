package com.Booking.Booking.System.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "verified_reviews")
public class VerifiedReview extends Review {

    @Column(nullable = false)
    private boolean appointmentConfirmed;

    //  Constructors 
    public VerifiedReview() {
        super();
    }

    public VerifiedReview(int rating, String comment, LocalDateTime createdAt,
                          User user, SalonService salonService, Appointment appointment,
                          boolean appointmentConfirmed) {
        super(rating, comment, createdAt, user, salonService, appointment);
        this.appointmentConfirmed = appointmentConfirmed;
    }

    //  Getters & Setters
    public boolean isAppointmentConfirmed() { return appointmentConfirmed; }
    public void setAppointmentConfirmed(boolean appointmentConfirmed) {
        this.appointmentConfirmed = appointmentConfirmed;
    }
}
