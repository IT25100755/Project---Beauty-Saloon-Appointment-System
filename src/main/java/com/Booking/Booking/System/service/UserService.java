package com.Booking.Booking.System.service;

import com.Booking.Booking.System.model.User;
import com.Booking.Booking.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired 
    private UserRepository userRepository;

    //  CREATE 
    public User saveUser(User user) {
        // Check for duplicate email before saving
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("An account with email '" + user.getEmail() + "' already exists.");
        }
        return userRepository.save(user);
    }

    //  READ 
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //  UPDATE 
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

    //  DELETE 
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
