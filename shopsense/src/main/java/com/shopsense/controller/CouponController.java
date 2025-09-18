package com.shopsense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopsense.service.CouponService;
import com.shopsense.entity.Coupon;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CouponController {

	@Autowired
	CouponService couponService;

	@GetMapping(value = "/coupon/check")
	public Coupon checkCoupon(@RequestParam String code) {
		return couponService.checkCoupon(code);
	}

	@GetMapping(value = "/coupon/all")
	public List<Coupon> getAllCoupons() {
		return couponService.getAllCoupons();
	}

	@PostMapping(value = "/coupon")
	public Coupon createCoupon(@RequestBody Coupon c) {
		return couponService.createCoupon(c);
	}

	@PutMapping(value = "/coupon")
	public boolean updateCoupon(@RequestBody Coupon c) {
		return couponService.updateCoupon(c);
	}

	@DeleteMapping(value = "/coupon")
	public boolean deleteCoupon(@RequestParam int couponId) {
		return couponService.deleteCoupon(couponId);
	}
}
