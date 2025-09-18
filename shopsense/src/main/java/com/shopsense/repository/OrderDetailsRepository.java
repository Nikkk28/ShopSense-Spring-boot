package com.shopsense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopsense.entity.OrderDetails;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
	
	List<OrderDetails> findByOrderId(int orderId);
	
	List<OrderDetails> findByOrderIdAndSellerId(int orderId, int sellerId);
	
	List<OrderDetails> findBySellerId(int sellerId);
	
	@Query("SELECT od.status FROM OrderDetails od WHERE od.orderId = :orderId")
	List<String> findStatusesByOrderId(@Param("orderId") int orderId);
	
	@Query("SELECT COUNT(od) > 0 FROM OrderDetails od WHERE od.orderId = :orderId AND od.status = 'Shipped'")
	boolean hasShippedItems(@Param("orderId") int orderId);
}
