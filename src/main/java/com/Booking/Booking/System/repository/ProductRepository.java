package com.Booking.Booking.System.repository;

import com.Booking.Booking.System.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Product.
 * Inherits full CRUD from JpaRepository.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
