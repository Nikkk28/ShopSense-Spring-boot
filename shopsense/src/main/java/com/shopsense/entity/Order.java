package com.shopsense.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private int id;
	
	@Column(name = "order_date")
	private Date orderDate;
	
	@Column(name = "order_total")
	private double orderTotal;
	
	@Column(name = "customer_id")
	private int customerId;
	
	@Column(name = "discount")
	private double discount;
	
	@Column(name = "shipping_charge")
	private double shippingCharge;
	
	@Column(name = "tax")
	private double tax;
	
	@Column(name = "shipping_street")
	private String shippingStreet;
	
	@Column(name = "shipping_city")
	private String shippingCity;
	
	@Column(name = "shipping_post_code")
	private String shippingPostCode;
	
	@Column(name = "shipping_state")
	private String shippingState;
	
	@Column(name = "shipping_country")
	private String shippingCountry;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "sub_total")
	private double subTotal;
	
	@Column(name = "payment_status")
	private String paymentStatus;
	
	@Column(name = "payment_method")
	private String paymentMethod;
	
	@Column(name = "card_number")
	private String cardNumber;
	
	@Column(name = "card_cvv")
	private String cardCvv;
	
	@Column(name = "card_holder_name")
	private String cardHolderName;
	
	@Column(name = "card_expiry_date")
	private String cardExpiryDate;
	
	@Column(name = "gateway_fee")
	private double gatewayFee;
	
	// Relationship with Customer
	@ManyToOne
	@JoinColumn(name = "customer_id", insertable = false, updatable = false)
	private Customer customer;
	
	// Relationship with OrderDetails
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderDetails> orderDetails;
}
