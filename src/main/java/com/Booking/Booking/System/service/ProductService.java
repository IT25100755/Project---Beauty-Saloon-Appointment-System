package com.Booking.Booking.System.service;

import com.Booking.Booking.System.model.Product;
import com.Booking.Booking.System.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Product management.
 * OOP Concept: Encapsulation — business logic for stock management.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // ─── CREATE ──────────────────────────────────────────────────────────────────
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

}
