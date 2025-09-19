package com.shopsense.service;

import java.sql.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopsense.entity.CartItem;
import com.shopsense.entity.Customer;
import com.shopsense.entity.Order;
import com.shopsense.entity.OrderDetails;
import com.shopsense.entity.Product;
import com.shopsense.entity.VerificationCode;
import com.shopsense.repository.CartItemRepository;
import com.shopsense.repository.CustomerRepository;
import com.shopsense.repository.OrderDetailsRepository;
import com.shopsense.repository.OrderRepository;
import com.shopsense.repository.ProductRepository;
import com.shopsense.repository.VerificationCodeRepository;

@Service
public class CustomerService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
	
	@Autowired
	private VerificationCodeRepository verificationCodeRepository;
	
//	@Autowired
//	private EmailService emailService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return customerRepository.findByEmailAndStatus(email, "Active")
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	public Customer signup(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer getCustomer(int customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new RuntimeException("Customer not found"));
		customer.setPassword(null); // Don't expose password
		return customer;
	}

	public Product getProduct(int productId) {
		return productRepository.findProductWithSeller(productId);
	}

	public List<Product> getProducts() {
		return productRepository.findAllActiveProducts();
	}

	public CartItem addToCart(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}

	public boolean updateCart(CartItem cartItem) {
		try {
			cartItemRepository.save(cartItem);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean removeFromCart(int cartId) {
		try {
			cartItemRepository.deleteById(cartId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<CartItem> getCartItems(int customerId) {
		return cartItemRepository.findByCustomerId(customerId);
	}

	@Transactional
	public Order placeOrder(Order order) {
		try {
			// Save order
			Order savedOrder = orderRepository.save(order);
			
			// Save order details
			for (OrderDetails orderDetail : order.getOrderDetails()) {
				orderDetail.setOrderId(savedOrder.getId());
				orderDetailsRepository.save(orderDetail);
			}
			
			// Clear cart
			cartItemRepository.deleteByCustomerId(order.getCustomerId());
			
			// Send email notifications
			sendOrderEmails(order);
			
			return savedOrder;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public Order getOrder(int orderId) {
		return orderRepository.findById(orderId)
			.orElseThrow(() -> new RuntimeException("Order not found"));
	}

	public List<Order> getOrders(int customerId) {
		return orderRepository.findByCustomerIdOrderByIdDesc(customerId);
	}

	public OrderDetails trackOrder(int orderDetailsId) {
		return orderDetailsRepository.findById(orderDetailsId)
			.orElseThrow(() -> new RuntimeException("Order details not found"));
	}

	public boolean isProductPurchased(int customerId, int productId) {
		return customerRepository.hasCustomerPurchasedProduct(customerId, productId);
	}

	public List<Product> searchProducts(String searchTerm) {
		return productRepository.searchActiveProducts(searchTerm);
	}

	public boolean sendVerificationCode(Customer customer) {
		try {
			Random random = new Random();
			int randomCode = random.nextInt(999999 - 100000 + 1) + 100000;

			// Delete existing codes
			verificationCodeRepository.deleteByUserId(customer.getId());

			// Save new code
			VerificationCode verificationCode = new VerificationCode();
			verificationCode.setUserId(customer.getId());
			verificationCode.setCode(randomCode);
			verificationCodeRepository.save(verificationCode);

			// Send email
//			emailService.sendContentEmail("humahfuj@gmail.com", "Verification Code",
//				"<h2>Verification code is : " + String.valueOf(randomCode) + "</h2>");

			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	@Transactional
	public boolean verifyCode(int userId, int code) {
		try {
			if (verificationCodeRepository.existsByUserIdAndCode(userId, code)) {
				// Send confirmation email
//				emailService.sendContentEmail("humahfuj@gmail.com", "Email Verified",
//					"<h2>Email verification is complete</h2>");

				// Delete verification code
				verificationCodeRepository.deleteByUserId(userId);

				// Update customer email verification status
				Customer customer = customerRepository.findById(userId)
					.orElseThrow(() -> new RuntimeException("Customer not found"));
				customer.setEmailVerified(true);
				customerRepository.save(customer);

				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	private void sendOrderEmails(Order order) {
		// Implementation for sending order confirmation emails
		// This would use the EmailService to send formatted emails
	}
}
