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

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private SalonServiceRepository salonServiceRepository;

    //  CREATE
    public Review addReview(Review review) {
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        if (review.getComment() == null || review.getComment().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be empty.");
        }

        if (review.getUser() == null || review.getUser().getId() == null) {
            throw new IllegalArgumentException("User information is required.");
        }
        boolean userExists = userRepository.existsById(review.getUser().getId());
        if (!userExists) {
            throw new IllegalArgumentException("User with ID " + review.getUser().getId() + " does not exist.");
        }

        if (review.getSalonService() == null || review.getSalonService().getId() == null) {
            throw new IllegalArgumentException("Salon service information is required.");
        }
        boolean serviceExists = salonServiceRepository.existsById(review.getSalonService().getId());
        if (!serviceExists) {
            throw new IllegalArgumentException("Salon service with ID " + review.getSalonService().getId() + " does not exist.");
        }

        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

}
