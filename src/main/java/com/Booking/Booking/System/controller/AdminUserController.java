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

    // GET /api/admins
    @GetMapping
    public ResponseEntity<List<AdminUser>> getAllAdmins() {
        return ResponseEntity.ok(adminUserService.getAllAdmins());
    }

    // GET /api/admins/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AdminUser> getAdminById(@PathVariable Long id) {
        Optional<AdminUser> admin = adminUserService.getAdminById(id);
        return admin.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/admins/{id}
    @PutMapping("/{id}")
    public ResponseEntity<AdminUser> updateAdmin(@PathVariable Long id,
                                                  @RequestBody AdminUser adminDetails) {
        AdminUser updatedAdmin = adminUserService.updateAdmin(id, adminDetails);
        if (updatedAdmin != null) {
            return ResponseEntity.ok(updatedAdmin);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/admins/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        adminUserService.deleteAdmin(id);
        return ResponseEntity.ok("Admin with ID " + id + " has been deleted.");
    }
}
