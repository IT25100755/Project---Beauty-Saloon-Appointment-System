package com.Booking.Booking.System.service;

import com.Booking.Booking.System.model.User;
import com.Booking.Booking.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for User.
 * Contains all business logic — keeps controllers thin.
 * OOP Concept: Encapsulation (separates business logic from web layer).
 */
@Service
public class UserService {

    @Autowired // Dependency Injection — Spring injects the repository automatically
    private UserRepository userRepository;

    // ─── CREATE ──────────────────────────────────────────────────────────────────
    public User saveUser(User user) {
        // Check for duplicate email before saving
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("An account with email '" + user.getEmail() + "' already exists.");
        }
        return userRepository.save(user);
    }

}
