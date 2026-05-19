package com.Booking.Booking.System.service;

import com.Booking.Booking.System.model.Staff;
import com.Booking.Booking.System.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Staff management.
 * OOP Concept: Encapsulation of business logic.
 */
@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    // ─── CREATE ──────────────────────────────────────────────────────────────────
    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    // ─── READ ────────────────────────────────────────────────────────────────────
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Optional<Staff> getStaffById(Long id) {
        return staffRepository.findById(id);
    }

    public List<Staff> getStaffByRole(String role) {
        return staffRepository.findByRole(role);
    }

    // ─── UPDATE ──────────────────────────────────────────────────────────────────
    public Staff updateStaff(Long id, Staff staffDetails) {
        Optional<Staff> optionalStaff = staffRepository.findById(id);
        if (optionalStaff.isPresent()) {
            Staff existingStaff = optionalStaff.get();
            existingStaff.setName(staffDetails.getName());
            existingStaff.setRole(staffDetails.getRole());
            existingStaff.setPhone(staffDetails.getPhone());
            return staffRepository.save(existingStaff);
        }
        return null;
    }

    // ─── DELETE ──────────────────────────────────────────────────────────────────
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
}
