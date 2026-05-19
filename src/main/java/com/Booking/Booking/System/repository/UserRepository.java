package com.Booking.Booking.System.repository;

import com.Booking.Booking.System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User.
 * Spring Data JPA provides automatic CRUD implementations — no need to write SQL!
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query: Find a user by their email address
    Optional<User> findByEmail(String email);
}
