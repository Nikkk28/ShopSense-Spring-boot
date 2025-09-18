package com.shopsense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopsense.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	List<Order> findByCustomerIdOrderByIdDesc(int customerId);
	
	@Query("SELECT DISTINCT o FROM Order o JOIN OrderDetails od ON o.id = od.orderId " +
		   "WHERE od.sellerId = :sellerId ORDER BY o.id DESC")
	List<Order> findOrdersBySellerId(@Param("sellerId") int sellerId);
	
	@Query("SELECT DISTINCT o FROM Order o JOIN OrderDetails od ON o.id = od.orderId " +
		   "WHERE od.status = 'Shipped' ORDER BY o.id DESC")
	List<Order> findShippedOrders();
	
	@Query("SELECT o FROM Order o JOIN OrderDetails od ON o.id = od.orderId " +
		   "ORDER BY o.id DESC")
	List<Order> findAllOrdersWithDetails();
}
