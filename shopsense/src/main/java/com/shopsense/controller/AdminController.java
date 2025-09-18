package com.shopsense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopsense.dto.AuthRequest;
import com.shopsense.dto.AuthResponse;
import com.shopsense.dto.StatusUpdate;
import com.shopsense.entity.Customer;
import com.shopsense.entity.Order;
import com.shopsense.entity.OrderDetails;
import com.shopsense.entity.Product;
import com.shopsense.entity.Seller;
import com.shopsense.service.AdminService;
import com.shopsense.service.AuthService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	AuthService authService;

	@PostMapping(value = "/admin/login")
	public AuthResponse login(@RequestBody AuthRequest a) {
		return authService.login(a);
	}

	@GetMapping(value = "/admin/products")
	public List<Product> getAllProducts() {
		return adminService.getAllProducts();
	}

	@PutMapping(value = "/admin/product")
	public Product updateProduct(@RequestBody Product a) {
		return adminService.updateProduct(a);
	}

	@GetMapping(value = "/admin/sellers")
	public List<Seller> getAllSellers() {
		return adminService.getAllSellers();
	}

	@PutMapping(value = "/admin/seller")
	public Seller updateSeller(@RequestBody StatusUpdate a) {
		return adminService.updateSeller(a);
	}

	@GetMapping(value = "/admin/customers")
	public List<Customer> getAllCustomers() {
		return adminService.getAllCustomers();
	}

	@PutMapping(value = "/admin/customer")
	public Customer updateCustomer(@RequestBody StatusUpdate a) {
		return adminService.updateCustomer(a);
	}

	@GetMapping(value = "/admin/orders")
	public List<Order> getOrders() {
		return adminService.getOrders();
	}

	@GetMapping(value = "/admin/order")
	public Order getOrder(@RequestParam("orderid") int orderId) {
		return adminService.getOrder(orderId);
	}

	@PutMapping(value = "/admin/order")
	public boolean updateOrder(@RequestBody OrderDetails p) {
		return adminService.updateOrder(p);
	}

	@GetMapping(value = "/admin/orders/shipped")
	public List<Order> getShippedOrders() {
		return adminService.getShippedOrders();
	}
}
