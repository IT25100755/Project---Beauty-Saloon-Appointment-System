package com.Booking.Booking.System.model;

import jakarta.persistence.*;

@Entity
@Table(name = "services")
public class SalonService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // e.g., "Haircut", "Manicure"

    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int durationMinutes; // How long the service takes

    //  Constructors 
    public SalonService() {}

    public SalonService(String name, String description, double price, int durationMinutes) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationMinutes = durationMinutes;
    }

    //  Getters & Setters 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
}
