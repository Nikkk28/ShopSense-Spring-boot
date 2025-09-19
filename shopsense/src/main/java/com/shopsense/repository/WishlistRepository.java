package com.shopsense.repository;

import java.util.List;

import com.shopsense.dto.WishlistDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shopsense.entity.Wishlist;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

	@Transactional
	void deleteByCustomerIdAndProductId(int customerId, int productId);

	boolean existsByCustomerIdAndProductId(int customerId, int productId);

	List<Wishlist> findAllByCustomerId(int customerId);

	@Query("SELECT new com.shopsense.dto.WishlistDetail(w.customerId, w.productId, p.title, p.thumbnailUrl, " +
			"CAST(p.salePrice AS double), p.stockStatus) " +
			"FROM Wishlist w JOIN Product p ON w.productId = p.id WHERE w.customerId = :customerId")
	List<WishlistDetail> findWishlistDetailsByCustomerId(@Param("customerId") int customerId);

}
