package com.shopsense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopsense.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	List<Product> findBySellerId(int sellerId);
	
	List<Product> findByStatus(String status);
	
	@Query("SELECT p FROM Product p JOIN Seller s ON p.sellerId = s.id " +
		   "WHERE p.status = 'Active' AND s.status = 'Active'")
	List<Product> findAllActiveProducts();
	
	@Query("SELECT p FROM Product p JOIN Seller s ON p.sellerId = s.id " +
		   "WHERE p.status = 'Active' AND s.status = 'Active' AND p.title LIKE %:searchTerm%")
	List<Product> searchActiveProducts(@Param("searchTerm") String searchTerm);
	
	@Query("SELECT p FROM Product p JOIN Seller s ON p.sellerId = s.id " +
		   "WHERE p.id = :productId")
	Product findProductWithSeller(@Param("productId") int productId);
}
