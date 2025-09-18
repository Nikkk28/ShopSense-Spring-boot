package com.shopsense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopsense.entity.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	
	Optional<Coupon> findByCode(String code);
	
	@Query("SELECT c FROM Coupon c WHERE c.code = :code AND c.status = 'Active' AND c.expiryDate >= CURRENT_DATE")
	Optional<Coupon> findValidCouponByCode(@Param("code") String code);
	
	boolean existsByCode(String code);
}
