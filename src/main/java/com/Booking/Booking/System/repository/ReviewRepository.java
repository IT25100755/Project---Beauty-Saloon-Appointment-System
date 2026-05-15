package com.Booking.Booking.System.repository;

import com.Booking.Booking.System.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findBySalonServiceId(Long serviceId);
    List<Review> findByUserId(Long userId);
}
