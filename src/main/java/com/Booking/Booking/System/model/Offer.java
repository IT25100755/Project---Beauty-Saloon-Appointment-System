package com.Booking.Booking.System.model;

import jakarta.persistence.*;

/**
 * Offer class for promotional discounts linked to a specific SalonService.
 * OOP Concept: Association (Offer has-a SalonService via @ManyToOne).
 */
@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // e.g., "Summer 20% Off Haircut"

    @Column(nullable = false)
    private double discountPercentage; // e.g., 20.0 means 20%

    // Foreign Key: Many Offers can apply to one SalonService
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", nullable = false)
    private SalonService salonService;

    // ─── Constructors ────────────────────────────────────────────────────────────
    public Offer() {}

    public Offer(String title, double discountPercentage, SalonService salonService) {
        this.title = title;
        this.discountPercentage = discountPercentage;
        this.salonService = salonService;
    }

    // ─── Business Logic Method ───────────────────────────────────────────────────
    /**
     * Calculates the final discounted price for the linked service.
     * OOP Concept: Encapsulation of business logic inside the model.
     */
    public double getDiscountedPrice() {
        if (salonService != null) {
            return salonService.getPrice() - (salonService.getPrice() * discountPercentage / 100);
        }
        return 0.0;
    }

    // ─── Getters & Setters ───────────────────────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; }

    public SalonService getSalonService() { return salonService; }
    public void setSalonService(SalonService salonService) { this.salonService = salonService; }
}
