package com.shopsense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopsense.entity.Coupon;
import com.shopsense.repository.CouponRepository;

@Service
public class CouponService {

	@Autowired
	private CouponRepository couponRepository;

	public List<Coupon> getAllCoupons() {
		return couponRepository.findAll();
	}

	public Coupon getCoupon(int couponId) {
		return couponRepository.findById(couponId)
				.orElseThrow(() -> new RuntimeException("Coupon not found"));
	}

	public Coupon getCouponByCode(String code) {
		return couponRepository.findByCode(code)
				.orElseThrow(() -> new RuntimeException("Coupon not found"));
	}

	public Coupon getValidCouponByCode(String code) {
		return couponRepository.findValidCouponByCode(code)
				.orElseThrow(() -> new RuntimeException("Invalid or expired coupon"));
	}

	public Coupon createCoupon(Coupon coupon) {
		return couponRepository.save(coupon);
	}

	public boolean updateCoupon(Coupon coupon) {
		try {
			couponRepository.save(coupon);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean deleteCoupon(int couponId) {
		try {
			couponRepository.deleteById(couponId);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean existsByCode(String code) {
		return couponRepository.existsByCode(code);
	}
}