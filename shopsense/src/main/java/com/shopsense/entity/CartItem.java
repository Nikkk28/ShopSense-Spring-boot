package com.shopsense.entity;

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
@Table(name = "carts")
public class CartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id")
	private int id;
	
	@Column(name = "customer_id")
	private int customerId;
	
	@Column(name = "product_id")
	private int productId;
	
	@Column(name = "seller_id")
	private int sellerId;
	
	@Column(name = "store_name")
	private String storeName;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "product_thumbnail_url")
	private String productThumbnailUrl;
	
	@Column(name = "product_unit_price")
	private double productUnitPrice;
	
	@Column(name = "quantity")
	private int productQuantity;
	
	@Column(name = "sub_total")
	private double subTotal;
	
	// Relationships
	@ManyToOne
	@JoinColumn(name = "customer_id", insertable = false, updatable = false)
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "product_id", insertable = false, updatable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "seller_id", insertable = false, updatable = false)
	private Seller seller;
}
