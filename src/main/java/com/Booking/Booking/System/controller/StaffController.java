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

    // GET /api/staff
    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    // GET /api/staff/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id) {
        Optional<Staff> staff = staffService.getStaffById(id);
        return staff.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/staff/role/{role}  — filter by role, e.g., "Hair Stylist"
    @GetMapping("/role/{role}")
    public ResponseEntity<List<Staff>> getStaffByRole(@PathVariable String role) {
        return ResponseEntity.ok(staffService.getStaffByRole(role));
    }

    // PUT /api/staff/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id,
                                              @RequestBody Staff staffDetails) {
        Staff updatedStaff = staffService.updateStaff(id, staffDetails);
        if (updatedStaff != null) {
            return ResponseEntity.ok(updatedStaff);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/staff/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.ok("Staff with ID " + id + " has been deleted.");
    }
}
