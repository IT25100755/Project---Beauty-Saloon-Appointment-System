package com.Booking.Booking.System.model;

import jakarta.persistence.*;

/**
 * Base User class representing a customer in the salon booking system.
 * OOP Concept: Encapsulation (private fields with public getters/setters)
 * OOP Concept: Inheritance Base Class (AdminUser extends this)
 */
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED) // Separate table for AdminUser (JOINED strategy)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true) // Email must be unique
    private String email;

    private String phone;

    // ─── Constructors ────────────────────────────────────────────────────────────
    public User() {}

    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // ─── Getters & Setters (Encapsulation) ──────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
