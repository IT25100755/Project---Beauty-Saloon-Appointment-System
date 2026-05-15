package com.Booking.Booking.System.service;

import com.Booking.Booking.System.model.Product;
import com.Booking.Booking.System.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    //  CREATE 
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    //  READ 
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    //  UPDATE 
    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(productDetails.getName());
            existingProduct.setBrand(productDetails.getBrand());
            existingProduct.setPrice(productDetails.getPrice());
            existingProduct.setStockQuantity(productDetails.getStockQuantity());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    //  DELETE 
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
