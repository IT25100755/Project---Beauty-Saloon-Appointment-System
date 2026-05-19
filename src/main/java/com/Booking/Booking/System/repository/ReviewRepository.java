package com.Booking.Booking.System.repository;

import com.Booking.Booking.System.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ReviewRepository — Data Access Layer for the Review entity.
 *
 * Spring Data JPA automatically implements CRUD methods (save, findById, findAll, deleteById, etc.)
 * just by extending JpaRepository. We only need to declare custom finder methods below.
 *
 * OOP Concept: Abstraction — the interface hides all SQL query details from the rest of the code.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Custom query: find all reviews for a specific salon service
    // Spring Data JPA translates the method name → SQL: SELECT * FROM reviews WHERE service_id = ?
    List<Review> findBySalonServiceId(Long serviceId);

    // Custom query: find all reviews submitted by a specific user
    // Spring Data JPA translates: SELECT * FROM reviews WHERE user_id = ?
    List<Review> findByUserId(Long userId);
}
