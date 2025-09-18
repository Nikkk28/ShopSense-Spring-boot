package com.shopsense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopsense.entity.Order;
import com.shopsense.entity.OrderDetails;
import com.shopsense.entity.Product;
import com.shopsense.entity.RevenueProfit;
import com.shopsense.entity.Seller;
import com.shopsense.repository.OrderDetailsRepository;
import com.shopsense.repository.OrderRepository;
import com.shopsense.repository.ProductRepository;
import com.shopsense.repository.RevenueProfitRepository;
import com.shopsense.repository.SellerRepository;

@Service
public class SellerService implements UserDetailsService {

	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
	
	@Autowired
	private RevenueProfitRepository revenueProfitRepository;
	
	@Autowired
	private EmailService emailService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return sellerRepository.findByEmailAndStatus(email, "Active")
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	public Seller signup(Seller seller) {
		return sellerRepository.save(seller);
	}

	public Seller getSeller(int sellerId) {
		Seller seller = sellerRepository.findById(sellerId)
			.orElseThrow(() -> new RuntimeException("Seller not found"));
		seller.setPassword(null); // Don't expose password
		return seller;
	}

	public Product getProduct(int productId) {
		return productRepository.findById(productId)
			.orElseThrow(() -> new RuntimeException("Product not found"));
	}

	public List<Product> getProducts(int sellerId) {
		return productRepository.findBySellerId(sellerId);
	}

	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	public boolean updateProduct(Product product) {
		try {
			productRepository.save(product);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean deleteProduct(int productId) {
		try {
			productRepository.deleteById(productId);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public List<Order> getOrders(int sellerId) {
		return orderRepository.findOrdersBySellerId(sellerId);
	}

	public Order getOrder(int orderId, int sellerId) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new RuntimeException("Order not found"));
		
		// Filter order details for this seller
		List<OrderDetails> sellerOrderDetails = orderDetailsRepository.findByOrderIdAndSellerId(orderId, sellerId);
		order.setOrderDetails(sellerOrderDetails);
		
		return order;
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
				.anyMatch(status -> status.equals("Pending") || status.equals("Processing") || status.equals("Shipped"))
				? "Processing" : "Completed";

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

			// Send status update email
			emailService.sendContentEmail("humahfuj@gmail.com", "Order Status Changed",
				"<h1>Update of your order status</h1>" +
				"<h2>Your order #" + orderDetails.getOrderId() + " is now " + orderDetails.getStatus() + "</h2>");

			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
}
