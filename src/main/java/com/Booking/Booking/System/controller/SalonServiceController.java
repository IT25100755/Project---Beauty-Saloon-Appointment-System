package com.Booking.Booking.System.controller;

import com.Booking.Booking.System.model.SalonService;
import com.Booking.Booking.System.service.SalonServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for SalonService (Catalog of services) API.
 * Exposes CRUD endpoints at /api/services
 */
@RestController
@RequestMapping("/api/services")
public class SalonServiceController {

    @Autowired
    private SalonServiceService salonServiceService;

    // POST /api/services
    @PostMapping
    public ResponseEntity<SalonService> createService(@RequestBody SalonService service) {
        SalonService savedService = salonServiceService.saveService(service);
        return new ResponseEntity<>(savedService, HttpStatus.CREATED);
    }

    // GET /api/services
    @GetMapping
    public ResponseEntity<List<SalonService>> getAllServices() {
        return ResponseEntity.ok(salonServiceService.getAllServices());
    }

    // GET /api/services/{id}
    @GetMapping("/{id}")
    public ResponseEntity<SalonService> getServiceById(@PathVariable Long id) {
        Optional<SalonService> service = salonServiceService.getServiceById(id);
        return service.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/services/{id}
    @PutMapping("/{id}")
    public ResponseEntity<SalonService> updateService(@PathVariable Long id,
                                                       @RequestBody SalonService serviceDetails) {
        SalonService updatedService = salonServiceService.updateService(id, serviceDetails);
        if (updatedService != null) {
            return ResponseEntity.ok(updatedService);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/services/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        salonServiceService.deleteService(id);
        return ResponseEntity.ok("Service with ID " + id + " has been deleted.");
    }
}
