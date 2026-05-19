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

    // ─── READ ────────────────────────────────────────────────────────────────────
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public Optional<Offer> getOfferById(Long id) {
        return offerRepository.findById(id);
    }

    public List<Offer> getOffersByServiceId(Long serviceId) {
        return offerRepository.findBySalonServiceId(serviceId);
    }

    // ─── UPDATE ──────────────────────────────────────────────────────────────────
    public Offer updateOffer(Long id, Offer offerDetails) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        if (optionalOffer.isPresent()) {
            Offer existingOffer = optionalOffer.get();
            existingOffer.setTitle(offerDetails.getTitle());
            existingOffer.setDiscountPercentage(offerDetails.getDiscountPercentage());
            // Optionally re-link to a different service if provided
            if (offerDetails.getSalonService() != null && offerDetails.getSalonService().getId() != null) {
                SalonService service = salonServiceRepository
                        .findById(offerDetails.getSalonService().getId())
                        .orElseThrow(() -> new RuntimeException("Salon Service not found."));
                existingOffer.setSalonService(service);
            }
            return offerRepository.save(existingOffer);
        }
        return null;
    }

    // ─── DELETE ──────────────────────────────────────────────────────────────────
    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }
}
