package com.Booking.Booking.System.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins") 
public class AdminUser extends User {

    private String adminLevel; 

    
    public AdminUser() {}

    public AdminUser(String name, String email, String phone, String adminLevel) {
        super(name, email, phone); 
        this.adminLevel = adminLevel;
    }

    public String getAdminLevel() {
        return adminLevel; }
    public void setAdminLevel(String adminLevel) { 
        this.adminLevel = adminLevel; }
}
