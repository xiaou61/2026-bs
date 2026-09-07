package com.xiaou.campusshare.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusshare.entity.BalanceRecord;
import com.xiaou.campusshare.entity.DepositRecord;
import com.xiaou.campusshare.entity.User;
import com.xiaou.campusshare.mapper.BalanceRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaymentService extends ServiceImpl<BalanceRecordMapper, BalanceRecord> {

    @Autowired
    private UserService userService;

    @Autowired
    private DepositRecordService depositRecordService;

    @Transactional
    public boolean chargeDeposit(Long userId, BigDecimal amount) {
        User user = userService.getById(userId);
        if (user == null) {
            return false;
        }

        BigDecimal before = user.getDepositBalance();
        BigDecimal after = before.add(amount);

        depositRecordService.addRecord(userId, null, "CHARGE", amount, before, after, "充值押金");
        return userService.updateDepositBalance(userId, amount);
    }

    @Transactional
    public boolean chargeBalance(Long userId, BigDecimal amount) {
        User user = userService.getById(userId);
        if (user == null) {
            return false;
        }

        BigDecimal before = user.getAccountBalance();
        BigDecimal after = before.add(amount);

        addBalanceRecord(userId, "CHARGE", amount, before, after, null, "充值余额");
        return userService.updateAccountBalance(userId, amount);
    }

    @Transactional
    public boolean freezeDeposit(Long userId, Long orderId, BigDecimal amount) {
        User user = userService.getById(userId);
        if (user == null || user.getDepositBalance().compareTo(amount) < 0) {
            return false;
        }

        BigDecimal before = user.getDepositBalance();
        BigDecimal after = before.subtract(amount);

        depositRecordService.addRecord(userId, orderId, "FREEZE", amount.negate(), before, after, "冻结押金");
        return userService.updateDepositBalance(userId, amount.negate());
    }

    @Transactional
    public boolean unfreezeDeposit(Long userId, Long orderId, BigDecimal amount) {
        User user = userService.getById(userId);
        if (user == null) {
            return false;
        }

        BigDecimal before = user.getDepositBalance();
        BigDecimal after = before.add(amount);

        depositRecordService.addRecord(userId, orderId, "UNFREEZE", amount, before, after, "解冻押金");
        return userService.updateDepositBalance(userId, amount);
    }

    public void addBalanceRecord(Long userId, String type, BigDecimal amount, BigDecimal before, BigDecimal after, Long orderId, String remark) {
        BalanceRecord record = new BalanceRecord();
        record.setUserId(userId);
        record.setRecordType(type);
        record.setAmount(amount);
        record.setBeforeBalance(before);
        record.setAfterBalance(after);
        record.setRelatedOrderId(orderId);
        record.setRemark(remark);
        save(record);
    }
}

