package com.shopsense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shopsense.dto.WithdrawalAdmin;
import com.shopsense.entity.Withdrawal;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Integer> {

    List<Withdrawal> findBySellerId(int sellerId);

    List<Withdrawal> findByStatus(String status);

    @Query("SELECT w FROM Withdrawal w WHERE w.sellerId = :sellerId AND w.status = :status ORDER BY w.requestDate DESC")
    List<Withdrawal> findBySellerIdAndStatus(@Param("sellerId") int sellerId, @Param("status") String status);

    @Query("SELECT w FROM Withdrawal w ORDER BY w.requestDate DESC")
    List<Withdrawal> findAllOrderByRequestDateDesc();

    @Modifying
    @Transactional
    @Query("UPDATE Withdrawal w SET w.status = :status, w.remarks = :remarks, w.paymentDate = CURRENT_DATE WHERE w.id = :id")
    int updateWithdrawalStatus(@Param("id") int id, @Param("status") String status, @Param("remarks") String remarks);

    @Query("SELECT new com.shopsense.dto.WithdrawalAdmin(w.id, w.sellerId, s.storeName, w.holderName, w.accountNumber, " +
            "w.bankName, w.branchName, w.requestDate, w.amount, w.paymentDate, w.status) " +
            "FROM Withdrawal w JOIN Seller s ON w.sellerId = s.id ORDER BY w.requestDate DESC")
    List<WithdrawalAdmin> findAllWithdrawalsForAdmin();

    @Query("SELECT SUM(w.amount) FROM Withdrawal w WHERE w.sellerId = :sellerId AND w.status = 'Processed'")
    Double getTotalWithdrawnAmount(@Param("sellerId") int sellerId);
}