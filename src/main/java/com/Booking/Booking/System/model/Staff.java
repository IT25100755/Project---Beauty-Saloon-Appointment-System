package com.Booking.Booking.System.model;

import jakarta.persistence.*;

/**
 * Staff class representing salon employees (stylists, therapists, etc.).
 * OOP Concept: Encapsulation (private fields with public getters/setters).
 */
@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role; // e.g., "Hair Stylist", "Massage Therapist"

    private String phone;

    // ─── Constructors ────────────────────────────────────────────────────────────
    public Staff() {}

    public Staff(String name, String role, String phone) {
        this.name = name;
        this.role = role;
        this.phone = phone;
    }

    // ─── Getters & Setters ───────────────────────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
