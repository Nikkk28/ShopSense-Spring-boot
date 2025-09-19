package com.shopsense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopsense.dto.StatusUpdate;
import com.shopsense.entity.Admin;
import com.shopsense.entity.Customer;
import com.shopsense.entity.Order;
import com.shopsense.entity.OrderDetails;
import com.shopsense.entity.Product;
import com.shopsense.entity.RevenueProfit;
import com.shopsense.entity.Seller;
import com.shopsense.repository.AdminRepository;
import com.shopsense.repository.CustomerRepository;
import com.shopsense.repository.OrderDetailsRepository;
import com.shopsense.repository.OrderRepository;
import com.shopsense.repository.ProductRepository;
import com.shopsense.repository.RevenueProfitRepository;
import com.shopsense.repository.SellerRepository;
//import com.shopsense.service.EmailService;

@Service
public class AdminService implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
	
	@Autowired
	private RevenueProfitRepository revenueProfitRepository;
	
//	@Autowired
//	private EmailService emailService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return adminRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product updateProduct(Product product) {
		return productRepository.save(product);
	}

	public List<Seller> getAllSellers() {
		return sellerRepository.findAll();
	}

	public Seller updateSeller(StatusUpdate statusUpdate) {
		Seller seller = sellerRepository.findById(statusUpdate.getId())
			.orElseThrow(() -> new RuntimeException("Seller not found"));
		seller.setStatus(statusUpdate.getStatus());
		return sellerRepository.save(seller);
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer updateCustomer(StatusUpdate statusUpdate) {
		Customer customer = customerRepository.findById(statusUpdate.getId())
			.orElseThrow(() -> new RuntimeException("Customer not found"));
		customer.setStatus(statusUpdate.getStatus());
		return customerRepository.save(customer);
	}

	public List<Order> getOrders() {
		return orderRepository.findAllOrdersWithDetails();
	}

	public Order getOrder(int orderId) {
		return orderRepository.findById(orderId)
			.orElseThrow(() -> new RuntimeException("Order not found"));
	}

	@Transactional
	public boolean updateOrder(OrderDetails orderDetails) {
		try {
			// Update order details status
			OrderDetails existingOrderDetails = orderDetailsRepository.findById(orderDetails.getId())
				.orElseThrow(() -> new RuntimeException("Order details not found"));
			existingOrderDetails.setStatus(orderDetails.getStatus());
			orderDetailsRepository.save(existingOrderDetails);

			// Check if all order details are completed
			List<String> statuses = orderDetailsRepository.findStatusesByOrderId(orderDetails.getOrderId());
			String orderStatus = statuses.stream()
				.allMatch(status -> status.equals("Canceled") || status.equals("Delivered") || status.equals("Refunded"))
				? "Completed" : "Processing";

			// Update main order status
			Order order = orderRepository.findById(orderDetails.getOrderId())
				.orElseThrow(() -> new RuntimeException("Order not found"));
			order.setStatus(orderStatus);
			orderRepository.save(order);

			// If delivered, create revenue profit record
			if (orderDetails.getStatus().equals("Delivered")) {
				RevenueProfit revenueProfit = new RevenueProfit();
				revenueProfit.setSellerId(orderDetails.getSellerId());
				revenueProfit.setOrderId(orderDetails.getOrderId());
				revenueProfit.setDeliveryDate(orderDetails.getDeliveryDate());
				revenueProfit.setOrderDetailsId(orderDetails.getId());
				revenueProfit.setRevenue(orderDetails.getSubTotal());
				revenueProfit.setCosts(0);
				revenueProfit.setPlatformProfit(revenueProfit.getRevenue() * 0.02); // 2% commission
				revenueProfit.setSellerProfit(revenueProfit.getRevenue() - revenueProfit.getPlatformProfit());
				
				revenueProfitRepository.save(revenueProfit);
				
				// Update seller balance
				sellerRepository.updateBalance(orderDetails.getSellerId(), revenueProfit.getSellerProfit());
			}

			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public List<Order> getShippedOrders() {
		return orderRepository.findShippedOrders();
	}
}
