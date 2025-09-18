package com.shopsense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopsense.dto.WithdrawalAdmin;
import com.shopsense.entity.Withdrawal;
import com.shopsense.repository.WithdrawalRepository;

@Service
public class WithdrawalService {

    @Autowired
    private WithdrawalRepository withdrawalRepository;

    public Withdrawal requestWithdraw(Withdrawal withdrawal) {
        return withdrawalRepository.save(withdrawal);
    }

    public List<Withdrawal> getWithdrawals(int sellerId) {
        return withdrawalRepository.findBySellerId(sellerId);
    }

    public boolean updateWithdraw(WithdrawalAdmin withdrawalAdmin) {
        return withdrawalRepository.updateWithdrawalStatus(
            withdrawalAdmin.getId(), 
            withdrawalAdmin.getStatus(), 
            withdrawalAdmin.getRemarks()
        ) > 0;
    }

    public List<WithdrawalAdmin> getAllWithdrawals() {
        return withdrawalRepository.findAllWithdrawalsForAdmin();
    }
}
