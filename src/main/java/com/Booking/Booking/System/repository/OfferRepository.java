package com.Booking.Booking.System.repository;

import com.Booking.Booking.System.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Offer.
 * Includes a custom method to find offers linked to a specific service.
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    // Find all offers for a specific salon service
    List<Offer> findBySalonServiceId(Long serviceId);
}
