package com.Booking.Booking.System.service;

import com.Booking.Booking.System.model.SalonService;
import com.Booking.Booking.System.repository.SalonServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for SalonService (the salon's offered services).
 * OOP Concept: Encapsulation.
 */
@Service
public class SalonServiceService {

    @Autowired
    private SalonServiceRepository salonServiceRepository;

    // ─── CREATE ──────────────────────────────────────────────────────────────────
    public SalonService saveService(SalonService service) {
        return salonServiceRepository.save(service);
    }
}
