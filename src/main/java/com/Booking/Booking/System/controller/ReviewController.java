package com.Booking.Booking.System.controller;

import com.Booking.Booking.System.model.Review;
import com.Booking.Booking.System.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    //  Inject the Service Layer 
    @Autowired
    private ReviewService reviewService;

    //  CREATE
    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody Review review) {
        try {
            Review saved = reviewService.addReview(review);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //  READ
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews()); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(ResponseEntity::ok)          // 200 OK if found
                     .orElse(ResponseEntity.notFound().build()); // 404 if not found
    }

    @GetMapping("/service/{id}")
    public ResponseEntity<List<Review>> getReviewsByService(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewsByService(id)); // 200 OK
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(id)); // 200 OK
    }

    //  UPDATE: PUT /api/reviews/update/{id} 
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody Review updated) {
        try {
            Review result = reviewService.updateReview(id, updated);
            if (result == null) {
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
            return ResponseEntity.ok(result); // 200 OK with updated review
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //  DELETE: DELETE /api/reviews/delete/{id} 
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review with ID " + id + " has been deleted successfully."); // 200 OK
    }
}
