package com.shopsense.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class WithdrawalAdmin {

	private int id;
	private int sellerId;
	private String storeName;
	private String holderName;
	private String accountNumber;
	private String bankName;
	private String branchName;
	private Date requestDate;
	private double amount;
	private Date paymentDate;
	private String status;
	private String remarks;
}
