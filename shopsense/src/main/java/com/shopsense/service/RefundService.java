package com.shopsense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopsense.entity.RefundDetails;
import com.shopsense.repository.RefundDetailsRepository;

@Service
public class RefundService {

    @Autowired
    private RefundDetailsRepository refundRepository;

    public RefundDetails createRefund(RefundDetails refund) {
        return refundRepository.save(refund);
    }

    public RefundDetails updateRefund(RefundDetails refund) {
        return refundRepository.save(refund);
    }

    public List<RefundDetails> getAllRefund() {
        return refundRepository.findAll();
    }

    public List<RefundDetails> getSellerRefund(int sellerId) {
        return refundRepository.findBySellerId(sellerId);
    }
}
