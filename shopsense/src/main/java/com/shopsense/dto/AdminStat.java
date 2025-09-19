package com.shopsense.dto;

import java.util.HashMap;
import java.util.List;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AdminStat {
	private double revenue;
	private double profit;
	private int sellers;
	private int customers;
	private int totalCustomers;
	private int totalSellers;
	private int products;
	private int totalProducts;
	private int orders;
	private long totalOrders;
	private double totalRevenue;
	private List<HashMap<String, String>> weeklyRevenue;
	private List<HashMap<String, String>> bestSeller;
	private List<HashMap<String, String>> orderStatus;
}
