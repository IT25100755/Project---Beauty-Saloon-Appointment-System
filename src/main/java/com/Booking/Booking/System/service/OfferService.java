package com.Booking.Booking.System.service;

import com.Booking.Booking.System.model.Offer;
import com.Booking.Booking.System.model.SalonService;
import com.Booking.Booking.System.repository.OfferRepository;
import com.Booking.Booking.System.repository.SalonServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Offer (discounts & promotions) management.
 * OOP Concept: Association — Offer is linked to SalonService.
 */
@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private SalonServiceRepository salonServiceRepository;

    // ─── CREATE (Offer must be linked to an existing SalonService) ────────────────
    public Offer saveOffer(Long serviceId, Offer offer) {
        SalonService service = salonServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Salon Service not found with id: " + serviceId));
        offer.setSalonService(service);
        return offerRepository.save(offer);
    }


}
