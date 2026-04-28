package com.Booking.Booking.System.controller;

import com.Booking.Booking.System.model.Staff;
import com.Booking.Booking.System.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Staff API.
 * Exposes CRUD endpoints at /api/staff
 */
@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    // POST /api/staff
    @PostMapping
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        Staff savedStaff = staffService.saveStaff(staff);
        return new ResponseEntity<>(savedStaff, HttpStatus.CREATED);
    }
}
