package com.Booking.Booking.System.repository;

import com.Booking.Booking.System.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Staff.
 * Spring Data JPA provides automatic CRUD operations.
 */
@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    // Find all staff members with a given role (e.g., all "Hair Stylists")
    List<Staff> findByRole(String role);
}
