package com.shopsense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopsense.entity.RefundDetails;

@Repository
public interface RefundDetailsRepository extends JpaRepository<RefundDetails, Integer> {

    List<RefundDetails> findBySellerId(int sellerId);

    List<RefundDetails> findByCustomerId(int customerId);

    List<RefundDetails> findByOrderId(int orderId);

    List<RefundDetails> findByStatus(String status);

    @Query("SELECT rd FROM RefundDetails rd WHERE rd.sellerId = :sellerId AND rd.status = :status")
    List<RefundDetails> findBySellerIdAndStatus(@Param("sellerId") int sellerId, @Param("status") String status);

    @Query("SELECT rd FROM RefundDetails rd WHERE rd.customerId = :customerId AND rd.status = :status")
    List<RefundDetails> findByCustomerIdAndStatus(@Param("customerId") int customerId, @Param("status") String status);

    boolean existsByOrderDetailsId(int orderDetailsId);
}