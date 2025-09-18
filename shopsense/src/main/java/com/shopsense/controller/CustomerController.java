package com.shopsense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopsense.service.CustomerService;
import com.shopsense.service.ProductService;
import com.shopsense.dto.AuthRequest;
import com.shopsense.dto.AuthResponse;
import com.shopsense.entity.CartItem;
import com.shopsense.entity.Customer;
import com.shopsense.entity.Order;
import com.shopsense.entity.OrderDetails;
import com.shopsense.entity.Product;
import com.shopsense.service.AuthService;

@CrossOrigin(origins = "*")
@RestController
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	AuthService authService;

	@PostMapping(value = "/customer/login")
	public AuthResponse login(@RequestBody AuthRequest a) {
		return authService.customerLogin(a);
	}

	@PostMapping(value = "/customer/signup")
	public Customer signup(@RequestBody Customer a) {
		return customerService.signup(a);
	}

	@GetMapping(value = "/customer/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		return customerService.getCustomer(customerId);
	}

	@GetMapping(value = "/product/{productId}")
	public Product getProduct(@PathVariable("productId") int productId) {
		return productService.getProduct(productId);
	}

	@GetMapping(value = "/products")
	public List<Product> getProducts() {
		return productService.getAllProducts();
	}

	@PostMapping(value = "/customer/cart")
	public CartItem addToCart(@RequestBody CartItem a) {
		return customerService.addToCart(a);
	}

	@PutMapping(value = "/customer/cart")
	public boolean updateCart(@RequestBody CartItem a) {
		return customerService.updateCart(a);
	}

	@DeleteMapping(value = "/customer/cart")
	public boolean removeFromCart(@RequestParam int id) {
		return customerService.removeFromCart(id);
	}

	@GetMapping(value = "/customer/cart")
	public List<CartItem> getCartItems(@RequestParam int id) {
		return customerService.getCartItems(id);
	}

	@PostMapping(value = "/customer/order")
	public Order placeOrder(@RequestBody Order a) {
		return customerService.placeOrder(a);
	}

	@GetMapping(value = "/customer/orders")
	public List<Order> getOrders(@RequestParam int id) {
		return customerService.getOrders(id);
	}

	@GetMapping(value = "/customer/order")
	public Order getOrder(@RequestParam int id) {
		return customerService.getOrder(id);
	}

	@GetMapping(value = "/customer/track")
	public OrderDetails trackOrder(@RequestParam int id) {
		return customerService.trackOrder(id);
	}

	@GetMapping(value = "/customer/check-purchased")
	public boolean isProductPurchased(@RequestParam int customerId, @RequestParam int productId) {
		return customerService.isProductPurchased(customerId, productId);
	}

	@GetMapping(value = "/search")
	public List<Product> searchProducts(@RequestParam String q) {
		return productService.searchProducts(q);
	}

	@PostMapping(value = "/customer/send-code")
	public boolean sendVerificationCode(@RequestBody Customer a) {
		return customerService.sendVerificationCode(a);
	}

	@GetMapping(value = "/customer/verify-code")
	public boolean verifyCode(@RequestParam int userId, @RequestParam int code) {
		return customerService.verifyCode(userId, code);
	}
}
