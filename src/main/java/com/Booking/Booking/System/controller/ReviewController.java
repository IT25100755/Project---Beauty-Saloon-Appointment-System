package com.Booking.Booking.System.controller;

import com.Booking.Booking.System.model.Review;
import com.Booking.Booking.System.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ReviewController — REST API Layer for Review & Feedback Management.
 *
 * Base URL: /api/reviews
 *
 * Endpoints:
 *  POST   /api/reviews/add              → Submit a new review
 *  GET    /api/reviews                  → Get all reviews (admin view)
 *  GET    /api/reviews/{id}             → Get a single review by ID
 *  GET    /api/reviews/service/{id}     → Get all reviews for a service
 *  GET    /api/reviews/user/{id}        → Get all reviews by a user
 *  PUT    /api/reviews/update/{id}      → Update a review (rating/comment)
 *  DELETE /api/reviews/delete/{id}      → Delete a review (user or admin)
 *
 * OOP Concept: Abstraction — the controller delegates all logic to ReviewService.
 *              It only handles HTTP concerns (request mapping, response codes).
 */
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    // ─── Inject the Service Layer ─────────────────────────────────────────────────
    @Autowired
    private ReviewService reviewService;

    // ─── CREATE: POST /api/reviews/add ───────────────────────────────────────────
    /**
     * Submit a new review.
     * Returns 201 Created on success, or 400 Bad Request if validation fails.
     *
     * Example JSON body:
     * {
     *   "rating": 5,
     *   "comment": "Amazing service! Very professional.",
     *   "user": { "id": 1 },
     *   "salonService": { "id": 2 },
     *   "appointment": { "id": 3 }   ← optional, can be omitted
     * }
     */
    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody Review review) {
        try {
            Review saved = reviewService.addReview(review);
            return new ResponseEntity<>(saved, HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e) {
            // Return 400 Bad Request with the validation error message
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ─── READ: GET /api/reviews ───────────────────────────────────────────────────
    /**
     * Get all reviews — typically used by the admin panel.
     * Returns 200 OK with a list of all Review objects.
     */
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews()); // 200 OK
    }

    // ─── READ: GET /api/reviews/{id} ─────────────────────────────────────────────
    /**
     * Get one specific review by its ID.
     * Returns 200 OK if found, or 404 Not Found if no review exists with that ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(ResponseEntity::ok)          // 200 OK if found
                     .orElse(ResponseEntity.notFound().build()); // 404 if not found
    }

    // ─── READ: GET /api/reviews/service/{id} ─────────────────────────────────────
    /**
     * Get all reviews for a particular salon service.
     * Used to display feedback on a service's page.
     *
     * Example: GET /api/reviews/service/2  → all reviews for service with ID 2
     */
    @GetMapping("/service/{id}")
    public ResponseEntity<List<Review>> getReviewsByService(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewsByService(id)); // 200 OK
    }

    // ─── READ: GET /api/reviews/user/{id} ────────────────────────────────────────
    /**
     * Get all reviews submitted by a specific user.
     * Used on the user's profile page to show their review history.
     *
     * Example: GET /api/reviews/user/1  → all reviews from user with ID 1
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(id)); // 200 OK
    }

    // ─── UPDATE: PUT /api/reviews/update/{id} ────────────────────────────────────
    /**
     * Update the rating and/or comment of an existing review.
     * Returns 200 OK with the updated review, or 404 if not found, or 400 if validation fails.
     *
     * Example JSON body:
     * {
     *   "rating": 4,
     *   "comment": "Good service, but waited a bit long."
     * }
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody Review updated) {
        try {
            Review result = reviewService.updateReview(id, updated);
            if (result == null) {
                // No review found with the given ID
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
            return ResponseEntity.ok(result); // 200 OK with updated review
        } catch (IllegalArgumentException e) {
            // Return 400 Bad Request with the validation error message
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ─── DELETE: DELETE /api/reviews/delete/{id} ─────────────────────────────────
    /**
     * Delete a review by its ID.
     * Can be used by the review's author (user deleting own review)
     * OR by an admin deleting an inappropriate review.
     *
     * Returns 200 OK with a confirmation message.
     *
     * Example: DELETE /api/reviews/delete/3
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review with ID " + id + " has been deleted successfully."); // 200 OK
    }
}
