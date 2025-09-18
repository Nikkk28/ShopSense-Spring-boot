package com.shopsense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopsense.service.RefundService;
import com.shopsense.entity.RefundDetails;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RefundController {

	@Autowired
	RefundService refundService;

	@PostMapping(value = "/customer/refund")
	public RefundDetails createRefund(@RequestBody RefundDetails a) {
		return refundService.createRefund(a);
	}
	
	@PutMapping(value = "/admin/refund")
	public RefundDetails updateRefund(@RequestBody RefundDetails a) {
		return refundService.updateRefund(a);
	}
	
	@GetMapping(value = "/admin/refund")
	public List<RefundDetails> getAllRefund() {
		return refundService.getAllRefund();
	}
	
	@GetMapping(value = "/seller/refund/{sellerId}")
	public List<RefundDetails> getSellerRefund(@PathVariable int sellerId) {
		return refundService.getSellerRefund(sellerId);
	}
}
