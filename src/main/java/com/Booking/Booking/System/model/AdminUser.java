package com.Booking.Booking.System.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * AdminUser class representing system administrators.
 * OOP Concept: Inheritance — inherits id, name, email, phone from User.
 * OOP Concept: Polymorphism — AdminUser IS-A User.
 */
@Entity
@Table(name = "admins") // Stores admin-specific data (adminLevel) in a separate table
public class AdminUser extends User {

    private String adminLevel; // e.g., "SuperAdmin", "Manager"

    // ─── Constructors ────────────────────────────────────────────────────────────
    public AdminUser() {}

    public AdminUser(String name, String email, String phone, String adminLevel) {
        super(name, email, phone); // Calls parent constructor (Inheritance)
        this.adminLevel = adminLevel;
    }

    // ─── Getters & Setters ───────────────────────────────────────────────────────
    public String getAdminLevel() { return adminLevel; }
    public void setAdminLevel(String adminLevel) { this.adminLevel = adminLevel; }
}
