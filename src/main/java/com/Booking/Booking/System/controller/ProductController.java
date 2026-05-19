package com.Booking.Booking.System.controller;

import com.Booking.Booking.System.model.Product;
import com.Booking.Booking.System.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST Controller for Product API.
 * Exposes CRUD endpoints at /api/products
 * OOP Concept: Abstraction — controller delegates all logic to ProductService.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Directory where uploaded product images are stored
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/products/";

    // ─── POST /api/products ───────────────────────────────────────────────────────
    // Accepts JSON body (no image). Image is uploaded separately via /upload-image
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // ─── GET /api/products ────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // ─── GET /api/products/{id} ───────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // ─── PUT /api/products/{id} ───────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                  @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }

    // ─── DELETE /api/products/{id} ────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product with ID " + id + " has been deleted.");
    }

    // ─── POST /api/products/{id}/upload-image ─────────────────────────────────────
    /**
     * Upload a product image and store it in /static/uploads/products/.
     * Returns the public URL path to store in the database.
     *
     * OOP Concept: Encapsulation — file handling is fully inside the controller.
     *
     * Accepted formats: jpg, jpeg, png, webp
     * Example response: { "imageUrl": "/uploads/products/product-7-abc123.jpg" }
     */
    @PostMapping("/{id}/upload-image")
    public ResponseEntity<?> uploadProductImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile file) {

        // 1. Validate the product exists
        Optional<Product> productOpt = productService.getProductById(id);
        if (productOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // 2. Validate file is not empty
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No image file provided.");
        }

        // 3. Validate file type (only images allowed)
        String originalName = file.getOriginalFilename() != null ? file.getOriginalFilename().toLowerCase() : "";
        if (!originalName.endsWith(".jpg") && !originalName.endsWith(".jpeg")
                && !originalName.endsWith(".png") && !originalName.endsWith(".webp")) {
            return ResponseEntity.badRequest()
                    .body("Invalid file type. Allowed: jpg, jpeg, png, webp.");
        }

        try {
            // 4. Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Files.createDirectories(uploadPath);

            // 5. Generate a unique filename to avoid conflicts
            String extension = originalName.substring(originalName.lastIndexOf('.'));
            String fileName  = "product-" + id + "-" + UUID.randomUUID().toString().substring(0, 8) + extension;
            Path   filePath  = uploadPath.resolve(fileName);

            // 6. Save the file
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 7. Build the public URL path (served by Spring static resources)
            String imageUrl = "/uploads/products/" + fileName;

            // 8. Update the product's imageUrl in the database
            Product product = productOpt.get();
            product.setImageUrl(imageUrl);
            productService.saveProduct(product);

            // 9. Return the URL so the frontend can display it
            return ResponseEntity.ok(java.util.Map.of("imageUrl", imageUrl));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Image upload failed: " + e.getMessage());
        }
    }
}
