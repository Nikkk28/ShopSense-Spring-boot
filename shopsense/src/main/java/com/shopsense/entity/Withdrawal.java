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
@Table(name = "withdrawals")
public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "withdrawal_id")
    private int id;

    @Column(name = "seller_id")
    private int sellerId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "holder_name")
    private String holderName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "request_date")
    private Date requestDate;

    @Column(name = "status")
    private String status; // Pending, Approved, Rejected, Processed

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "transaction_id")
    private String transactionId;

    // Relationship with Seller
    @ManyToOne
    @JoinColumn(name = "seller_id", insertable = false, updatable = false)
    private Seller seller;
}