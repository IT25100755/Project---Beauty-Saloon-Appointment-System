package com.Booking.Booking.System.controller;

import com.Booking.Booking.System.model.AdminUser;
import com.Booking.Booking.System.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for AdminUser API.
 * Exposes CRUD endpoints at /api/admins
 */
@RestController
@RequestMapping("/api/admins")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    // POST /api/admins
    @PostMapping
    public ResponseEntity<AdminUser> createAdmin(@RequestBody AdminUser admin) {
        AdminUser savedAdmin = adminUserService.saveAdmin(admin);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }
}
