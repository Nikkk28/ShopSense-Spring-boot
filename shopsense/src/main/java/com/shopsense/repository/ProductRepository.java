package com.shopsense.repository;

import java.util.HashMap;
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

	@Query("SELECT COUNT(p) FROM Product p WHERE p.status = 'Active'")
	int countActiveProducts();

	@Query("SELECT COUNT(p) FROM Product p WHERE p.sellerId = :sellerId AND p.status = 'Active'")
	int countProductsBySeller(@Param("sellerId") int sellerId);

	@Query("SELECT new java.util.HashMap(map('productId', p.id, 'title', p.title, 'category', p.category, 'salePrice', p.salePrice, 'stockCount', p.stockCount, 'status', p.status, 'storeName', s.storeName)) " +
			"FROM Product p JOIN Seller s ON p.sellerId = s.id")
	List<HashMap<String, Object>> getProductDetailsReport();

	@Query("SELECT new java.util.HashMap(map('productId', od.productId, 'productName', od.productName, 'totalSales', COUNT(od.id), 'totalRevenue', SUM(od.subTotal))) " +
			"FROM OrderDetails od GROUP BY od.productId, od.productName ORDER BY COUNT(od.id) DESC")
	List<HashMap<String, Object>> getFavoriteItemReport();

	@Query("SELECT new java.util.HashMap(map('productId', p.id, 'title', p.title, 'stockCount', p.stockCount, 'storeName', s.storeName)) " +
			"FROM Product p JOIN Seller s ON p.sellerId = s.id WHERE p.sellerId = :sellerId AND CAST(p.stockCount AS int) < 10")
	List<HashMap<String, String>> getStockAlertReport(@Param("sellerId") int sellerId);

	@Query("SELECT new java.util.HashMap(map('productId', od.productId, 'productName', od.productName, 'totalSold', SUM(od.quantity), 'totalRevenue', SUM(od.subTotal))) " +
			"FROM OrderDetails od WHERE od.sellerId = :sellerId GROUP BY od.productId, od.productName ORDER BY SUM(od.quantity) DESC")
	List<HashMap<String, String>> getTopSellingReport(@Param("sellerId") int sellerId);
	
	@Query("SELECT p FROM Product p JOIN Seller s ON p.sellerId = s.id " +
		   "WHERE p.status = 'Active' AND s.status = 'Active' AND p.title LIKE %:searchTerm%")
	List<Product> searchActiveProducts(@Param("searchTerm") String searchTerm);
	
	@Query("SELECT p FROM Product p JOIN Seller s ON p.sellerId = s.id " +
		   "WHERE p.id = :productId")
	Product findProductWithSeller(@Param("productId") int productId);
}
