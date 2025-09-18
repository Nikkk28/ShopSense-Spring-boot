package com.shopsense.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "revenue_profit")
public class RevenueProfit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "seller_id")
	private int sellerId;
	
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "order_date")
	private Date deliveryDate;
	
	@Column(name = "order_details_id")
	private int orderDetailsId;
	
	@Column(name = "revenue")
	private double revenue;
	
	@Column(name = "costs")
	private double costs;
	
	@Column(name = "platform_profit")
	private double platformProfit;
	
	@Column(name = "seller_profit")
	private double sellerProfit;
	
	// Relationships
	@ManyToOne
	@JoinColumn(name = "seller_id", insertable = false, updatable = false)
	private Seller seller;
	
	@ManyToOne
	@JoinColumn(name = "order_id", insertable = false, updatable = false)
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "order_details_id", insertable = false, updatable = false)
	private OrderDetails orderDetails;
}
