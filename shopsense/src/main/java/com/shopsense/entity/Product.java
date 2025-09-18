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
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "thumbnail_url")
	private String thumbnailUrl;
	
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "regular_price")
	private String regularPrice;
	
	@Column(name = "sale_price")
	private String salePrice;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "stock_status")
	private String stockStatus;
	
	@Column(name = "stock_count")
	private String stockCount;
	
	@Column(name = "seller_id")
	private int sellerId;
	
	@Column(name = "status")
	private String status;
	
	// Transient field for store name (will be populated via join)
	@Column(name = "store_name", insertable = false, updatable = false)
	private String storeName;
	
	// Optional: Add relationship to Seller entity
	@ManyToOne
	@JoinColumn(name = "seller_id", insertable = false, updatable = false)
	private Seller seller;
}
