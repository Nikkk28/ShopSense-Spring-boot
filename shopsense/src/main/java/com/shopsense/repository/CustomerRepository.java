package com.shopsense.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopsense.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	Optional<Customer> findByEmail(String email);
	
	Optional<Customer> findByEmailAndStatus(String email, String status);
	
	boolean existsByEmail(String email);
	@Query("SELECT COUNT(c) FROM Customer c WHERE c.status = 'Active'")
	int countActiveCustomers();

	@Query("SELECT new java.util.HashMap(map('customerId', c.id, 'name', c.name, 'email', c.email, 'totalOrders', " +
			"(SELECT COUNT(o2.id) FROM Order o2 WHERE o2.customerId = c.id), " +
			"'totalSpent', (SELECT COALESCE(SUM(o3.orderTotal), 0) FROM Order o3 WHERE o3.customerId = c.id))) " +
			"FROM Customer c WHERE c.status = 'Active'")
	List<HashMap<String, String>> getCustomerReport();
	
	List<Customer> findAllByStatus(String status);
	
	@Query("SELECT COUNT(c) > 0 FROM Customer c JOIN Order o ON c.id = o.customerId " +
		   "JOIN OrderDetails od ON o.id = od.orderId WHERE c.id = :customerId AND od.productId = :productId")
	boolean hasCustomerPurchasedProduct(@Param("customerId") int customerId, @Param("productId") int productId);
}
