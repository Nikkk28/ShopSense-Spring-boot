package com.shopsense.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopsense.entity.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {
	
	Optional<Seller> findByEmail(String email);
	
	Optional<Seller> findByEmailAndStatus(String email, String status);
	
	boolean existsByEmail(String email);
	@Query("SELECT COUNT(s) FROM Seller s WHERE s.status = 'Active'")
	int countActiveSellers();

	@Query("SELECT new java.util.HashMap(map('sellerId', s.id, 'storeName', s.storeName, 'totalOrders', COUNT(DISTINCT od.orderId), 'totalRevenue', COALESCE(SUM(od.subTotal), 0))) " +
			"FROM Seller s LEFT JOIN OrderDetails od ON s.id = od.sellerId GROUP BY s.id, s.storeName")
	List<HashMap<String, Object>> getSellerReport();

	List<Seller> findAllByStatus(String status);
	
	@Modifying
	@Query("UPDATE Seller s SET s.balance = s.balance + :amount WHERE s.id = :sellerId")
	void updateBalance(@Param("sellerId") int sellerId, @Param("amount") double amount);
}
