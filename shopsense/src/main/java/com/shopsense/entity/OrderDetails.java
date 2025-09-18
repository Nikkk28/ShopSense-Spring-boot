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
@Table(name = "order_details")
public class OrderDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_details_id")
	private int id;
	
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "product_id")
	private int productId;
	
	@Column(name = "seller_id")
	private int sellerId;
	
	@Column(name = "store_name")
	private String storeName;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "product_unit_price")
	private double productUnitPrice;
	
	@Column(name = "product_thumbnail_url")
	private String productThumbnailUrl;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "sub_total")
	private double subTotal;
	
	@Column(name = "delivery_date")
	private Date deliveryDate;
	
	// Relationships
	@ManyToOne
	@JoinColumn(name = "order_id", insertable = false, updatable = false)
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "product_id", insertable = false, updatable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "seller_id", insertable = false, updatable = false)
	private Seller seller;
}
