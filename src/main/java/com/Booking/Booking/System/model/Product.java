package com.Booking.Booking.System.model;

import jakarta.persistence.*;

/**
 * Product class representing a physical item sold by the salon (e.g., Shampoo, Conditioner).
 * OOP Concept: Encapsulation.
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String brand;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stockQuantity; // Number of items in stock

    // Image path stored after upload, e.g. /uploads/products/product-123.jpg
    // Nullable — products without images show a placeholder on frontend
    @Column
    private String imageUrl;

    // ─── Constructors ────────────────────────────────────────────────────────────
    public Product() {}

    public Product(String name, String brand, double price, int stockQuantity) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // ─── Getters & Setters ───────────────────────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
