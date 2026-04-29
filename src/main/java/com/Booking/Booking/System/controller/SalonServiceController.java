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
}
