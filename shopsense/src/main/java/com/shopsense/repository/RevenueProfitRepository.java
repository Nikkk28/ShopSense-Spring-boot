package com.shopsense.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopsense.entity.RevenueProfit;

@Repository
public interface RevenueProfitRepository extends JpaRepository<RevenueProfit, Integer> {
	
	List<RevenueProfit> findBySellerId(int sellerId);
	
	List<RevenueProfit> findByOrderId(int orderId);
	
	@Query("SELECT SUM(rp.platformProfit) FROM RevenueProfit rp")
	Double getTotalPlatformProfit();

	@Query("SELECT new java.util.HashMap(map('date', DATE(rp.deliveryDate), 'sales', SUM(rp.revenue), 'platformProfit', SUM(rp.platformProfit))) " +
			"FROM RevenueProfit rp GROUP BY DATE(rp.deliveryDate) ORDER BY DATE(rp.deliveryDate)")
	List<HashMap<String, String>> getAdminProfitReport();
	
	@Query("SELECT SUM(rp.sellerProfit) FROM RevenueProfit rp WHERE rp.sellerId = :sellerId")
	Double getTotalSellerProfit(@Param("sellerId") int sellerId);
	
	@Query("SELECT SUM(rp.revenue) FROM RevenueProfit rp")
	Double getTotalRevenue();
}
