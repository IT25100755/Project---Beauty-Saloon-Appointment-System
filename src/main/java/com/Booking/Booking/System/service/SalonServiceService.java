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

    // ─── READ ────────────────────────────────────────────────────────────────────
    public List<SalonService> getAllServices() {
        return salonServiceRepository.findAll();
    }

    public Optional<SalonService> getServiceById(Long id) {
        return salonServiceRepository.findById(id);
    }

    // ─── UPDATE ──────────────────────────────────────────────────────────────────
    public SalonService updateService(Long id, SalonService serviceDetails) {
        Optional<SalonService> optionalService = salonServiceRepository.findById(id);
        if (optionalService.isPresent()) {
            SalonService existingService = optionalService.get();
            existingService.setName(serviceDetails.getName());
            existingService.setDescription(serviceDetails.getDescription());
            existingService.setPrice(serviceDetails.getPrice());
            existingService.setDurationMinutes(serviceDetails.getDurationMinutes());
            return salonServiceRepository.save(existingService);
        }
        return null;
    }

    // ─── DELETE ──────────────────────────────────────────────────────────────────
    public void deleteService(Long id) {
        salonServiceRepository.deleteById(id);
    }
}
