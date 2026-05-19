package com.Booking.Booking.System.controller;

import com.Booking.Booking.System.model.Offer;
import com.Booking.Booking.System.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Offer (Discounts & Promotions) API.
 * Exposes endpoints at /api/offers
 */
@RestController
@RequestMapping("/api/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    // ─── CREATE Offer (linked to a service) ──────────────────────────────────────
    // POST /api/offers/service/{serviceId}
    @PostMapping("/service/{serviceId}")
    public ResponseEntity<?> createOffer(@PathVariable Long serviceId,
                                           @RequestBody Offer offer) {
        try {
            Offer savedOffer = offerService.saveOffer(serviceId, offer);
            return new ResponseEntity<>(savedOffer, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ─── READ All Offers ─────────────────────────────────────────────────────────
    // GET /api/offers
    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers() {
        return ResponseEntity.ok(offerService.getAllOffers());
    }

    // ─── READ Offer by ID ────────────────────────────────────────────────────────
    // GET /api/offers/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable Long id) {
        Optional<Offer> offer = offerService.getOfferById(id);
        return offer.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // ─── READ Offers by Service ID ───────────────────────────────────────────────
    // GET /api/offers/service/{serviceId}
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<Offer>> getOffersByService(@PathVariable Long serviceId) {
        return ResponseEntity.ok(offerService.getOffersByServiceId(serviceId));
    }

    // ─── UPDATE Offer ────────────────────────────────────────────────────────────
    // PUT /api/offers/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable Long id,
                                              @RequestBody Offer offerDetails) {
        Offer updatedOffer = offerService.updateOffer(id, offerDetails);
        if (updatedOffer != null) {
            return ResponseEntity.ok(updatedOffer);
        }
        return ResponseEntity.notFound().build();
    }

    // ─── DELETE Offer ────────────────────────────────────────────────────────────
    // DELETE /api/offers/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
        return ResponseEntity.ok("Offer with ID " + id + " has been deleted.");
    }
}
