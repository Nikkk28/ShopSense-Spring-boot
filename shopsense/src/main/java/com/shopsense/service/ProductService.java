package com.shopsense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopsense.entity.Product;
import com.shopsense.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAllActiveProducts();
    }

    public Product getProduct(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product getProductWithSeller(int productId) {
        return productRepository.findProductWithSeller(productId);
    }

    public List<Product> getProductsBySeller(int sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

    public List<Product> getProductsByStatus(String status) {
        return productRepository.findByStatus(status);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public boolean updateProduct(Product product) {
        try {
            productRepository.save(product);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean deleteProduct(int productId) {
        try {
            productRepository.deleteById(productId);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public List<Product> searchProducts(String searchTerm) {
        return productRepository.searchActiveProducts(searchTerm);
    }

    public boolean existsById(int productId) {
        return productRepository.existsById(productId);
    }
}