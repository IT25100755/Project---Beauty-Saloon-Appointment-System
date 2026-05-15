package com.Booking.Booking.System.repository;

import com.Booking.Booking.System.model.SalonService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonServiceRepository extends JpaRepository<SalonService, Long> {
}
