package com.Booking.Booking.System.service;

import com.Booking.Booking.System.model.Review;
import com.Booking.Booking.System.repository.ReviewRepository;
import com.Booking.Booking.System.repository.UserRepository;
import com.Booking.Booking.System.repository.SalonServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * ReviewService — Business Logic Layer for Review & Feedback Management.
 *
 * OOP Concepts Applied:
 *  - Abstraction   : All business rules are hidden here, away from the controller.
 *  - Encapsulation : This class controls how reviews are validated and persisted.
 *
 * All validation logic (rating range, non-empty comment, user & service existence)
 * is placed here so the controller stays clean and simple.
 */
@Service
public class ReviewService {

    // ─── Injected Repositories ────────────────────────────────────────────────────
    @Autowired
    private ReviewRepository reviewRepository;    // For all review CRUD operations

    @Autowired
    private UserRepository userRepository;        // To verify the user exists before saving

    @Autowired
    private SalonServiceRepository salonServiceRepository; // To verify the service exists

    // ─── CREATE: Submit a new review ─────────────────────────────────────────────
    /**
     * Saves a new Review after validating all fields.
     * Throws IllegalArgumentException with a descriptive message if validation fails.
     *
     * @param review The Review object received from the controller (from JSON body)
     * @return The saved Review with its auto-generated ID
     */
    public Review addReview(Review review) {

        // Validation 1: Rating must be between 1 and 5 (inclusive)
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        // Validation 2: Comment cannot be null or blank/empty
        if (review.getComment() == null || review.getComment().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be empty.");
        }

        // Validation 3: The User must exist in the database
        if (review.getUser() == null || review.getUser().getId() == null) {
            throw new IllegalArgumentException("User information is required.");
        }
        boolean userExists = userRepository.existsById(review.getUser().getId());
        if (!userExists) {
            throw new IllegalArgumentException("User with ID " + review.getUser().getId() + " does not exist.");
        }

        // Validation 4: The SalonService must exist in the database
        if (review.getSalonService() == null || review.getSalonService().getId() == null) {
            throw new IllegalArgumentException("Salon service information is required.");
        }
        boolean serviceExists = salonServiceRepository.existsById(review.getSalonService().getId());
        if (!serviceExists) {
            throw new IllegalArgumentException("Salon service with ID " + review.getSalonService().getId() + " does not exist.");
        }

        // Set the submission timestamp automatically (do not rely on user input for this)
        review.setCreatedAt(LocalDateTime.now());

        // Save and return the review (JPA generates the INSERT SQL automatically)
        return reviewRepository.save(review);
    }

    // ─── READ: Get all reviews ────────────────────────────────────────────────────
    /**
     * Returns every review in the database.
     * Used by admins to view all reviews.
     *
     * @return List of all Review objects
     */
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // ─── READ: Get a single review by its ID ──────────────────────────────────────
    /**
     * Finds a specific review by its primary key.
     *
     * @param id The review's database ID
     * @return Optional containing the Review if found, or empty if not found
     */
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    // ─── READ: Get all reviews for a specific service ─────────────────────────────
    /**
     * Returns reviews filtered by salon service ID.
     * Used on a service's page to show its feedback.
     *
     * @param serviceId The ID of the SalonService
     * @return List of reviews for that service
     */
    public List<Review> getReviewsByService(Long serviceId) {
        return reviewRepository.findBySalonServiceId(serviceId);
    }

    // ─── READ: Get all reviews submitted by a specific user ───────────────────────
    /**
     * Returns reviews filtered by user ID.
     * Allows a user to see their own review history.
     *
     * @param userId The ID of the User
     * @return List of reviews submitted by that user
     */
    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    // ─── UPDATE: Edit an existing review ─────────────────────────────────────────
    /**
     * Updates the rating and/or comment of an existing review.
     * Returns null if the review with the given ID does not exist.
     *
     * @param id      The ID of the review to update
     * @param updated A Review object containing the new rating and comment
     * @return The updated Review saved to the database, or null if not found
     */
    public Review updateReview(Long id, Review updated) {

        // Try to find the existing review by ID
        Optional<Review> existingOpt = reviewRepository.findById(id);

        if (existingOpt.isPresent()) {
            Review existing = existingOpt.get();

            // Validate the new rating if it was provided
            if (updated.getRating() < 1 || updated.getRating() > 5) {
                throw new IllegalArgumentException("Rating must be between 1 and 5.");
            }

            // Validate the new comment
            if (updated.getComment() == null || updated.getComment().trim().isEmpty()) {
                throw new IllegalArgumentException("Comment cannot be empty.");
            }

            // Apply the updated values (only rating and comment can be changed)
            existing.setRating(updated.getRating());
            existing.setComment(updated.getComment());

            // Save and return the updated review
            return reviewRepository.save(existing);
        }

        // Return null if no review was found with this ID (controller handles 404)
        return null;
    }

    // ─── DELETE: Remove a review by ID ────────────────────────────────────────────
    /**
     * Deletes a review from the database.
     * Can be called by the review's author (user) or by an admin.
     * No exception is thrown if the ID does not exist (silent no-op).
     *
     * @param id The ID of the review to delete
     */
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
