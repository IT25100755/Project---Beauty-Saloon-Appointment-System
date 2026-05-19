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
        // Public registration ALWAYS gets MEMBER role — never allow admin from register form
        user.setRole(User.ROLE_MEMBER);
        return userRepository.save(user);
    }

    // ─── READ ────────────────────────────────────────────────────────────────────
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // ─── UPDATE ──────────────────────────────────────────────────────────────────
    public User updateUser(Long id, User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setName(userDetails.getName());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPhone(userDetails.getPhone());
            return userRepository.save(existingUser);
        }
        return null; // Returns null if user not found (controller returns 404)
    }

    // ─── UPDATE ROLE (Admin-only action) ──────────────────────────────────────────────
    /**
     * Changes the role of an existing user.
     * Only valid roles are: MEMBER, ADMIN.
     * Called from PUT /api/users/{id}/role endpoint.
     * The frontend admin panel is the only place that triggers this.
     */
    public User updateUserRole(Long userId, String newRole) {
        // Validate role value
        if (newRole == null || newRole.isBlank()) {
            throw new RuntimeException("Role cannot be empty.");
        }
        if (!newRole.equals(User.ROLE_MEMBER) && !newRole.equals(User.ROLE_ADMIN)) {
            throw new RuntimeException("Invalid role: '" + newRole + "'. Valid roles are: MEMBER, ADMIN.");
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User with ID " + userId + " not found.");
        }

        User user = userOptional.get();
        user.setRole(newRole);
        return userRepository.save(user); // saves the updated role to DB
    }

    // ─── DELETE ──────────────────────────────────────────────────────────────────
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
