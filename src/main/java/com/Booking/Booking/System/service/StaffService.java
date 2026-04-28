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

}
