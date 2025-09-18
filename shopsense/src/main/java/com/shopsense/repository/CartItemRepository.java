package com.shopsense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopsense.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	
	List<CartItem> findByCustomerId(int customerId);
	
	@Modifying
	@Query("DELETE FROM CartItem c WHERE c.customerId = :customerId")
	void deleteByCustomerId(@Param("customerId") int customerId);
	
	boolean existsByCustomerIdAndProductId(int customerId, int productId);
}
