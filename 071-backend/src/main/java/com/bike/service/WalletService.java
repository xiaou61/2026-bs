package com.bike.service;

import com.bike.common.BusinessException;
import com.bike.entity.User;
import com.bike.entity.WalletRecord;
import com.bike.mapper.UserMapper;
import com.bike.mapper.WalletRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WalletRecordMapper walletRecordMapper;

    @Transactional
    public void recharge(Long userId, BigDecimal amount) {
        userMapper.updateBalance(userId, amount);
        User user = userMapper.findById(userId);
        WalletRecord record = new WalletRecord();
        record.setUserId(userId);
        record.setType(1);
        record.setAmount(amount);
        record.setBalanceAfter(user.getBalance());
        record.setDescription("账户充值");
        walletRecordMapper.insert(record);
    }

    public PageInfo<WalletRecord> getRecords(Long userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(walletRecordMapper.findByUserId(userId));
    }

    @Transactional
    public void payDeposit(Long userId) {
        User user = userMapper.findById(userId);
        if (user.getDepositPaid() == 1) {
            throw new BusinessException("已缴纳押金");
        }
        BigDecimal depositAmount = new BigDecimal("99.00");
        if (user.getBalance().compareTo(depositAmount) < 0) {
            throw new BusinessException("余额不足，请先充值");
        }
        userMapper.updateBalance(userId, depositAmount.negate());
        userMapper.updateDeposit(userId, 1);
        User updated = userMapper.findById(userId);
        WalletRecord record = new WalletRecord();
        record.setUserId(userId);
        record.setType(3);
        record.setAmount(depositAmount.negate());
        record.setBalanceAfter(updated.getBalance());
        record.setDescription("缴纳押金");
        walletRecordMapper.insert(record);
    }

    @Transactional
    public void refundDeposit(Long userId) {
        User user = userMapper.findById(userId);
        if (user.getDepositPaid() != 1) {
            throw new BusinessException("未缴纳押金");
        }
        BigDecimal depositAmount = new BigDecimal("99.00");
        userMapper.updateBalance(userId, depositAmount);
        userMapper.updateDeposit(userId, 0);
        User updated = userMapper.findById(userId);
        WalletRecord record = new WalletRecord();
        record.setUserId(userId);
        record.setType(4);
        record.setAmount(depositAmount);
        record.setBalanceAfter(updated.getBalance());
        record.setDescription("退还押金");
        walletRecordMapper.insert(record);
    }
}
