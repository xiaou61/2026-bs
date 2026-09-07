package com.xiaou.campusshare.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusshare.entity.User;
import com.xiaou.campusshare.entity.Withdrawal;
import com.xiaou.campusshare.mapper.WithdrawalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class WithdrawalService extends ServiceImpl<WithdrawalMapper, Withdrawal> {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @Transactional
    public Withdrawal apply(Long userId, BigDecimal amount, String withdrawType, String accountName, String accountNo) {
        if (amount == null || amount.compareTo(BigDecimal.TEN) < 0) {
            throw new IllegalArgumentException("提现金额不能低于10元");
        }
        if (withdrawType == null || withdrawType.isBlank()) {
            throw new IllegalArgumentException("提现方式不能为空");
        }
        if (accountName == null || accountName.isBlank() || accountNo == null || accountNo.isBlank()) {
            throw new IllegalArgumentException("账户信息不能为空");
        }

        User user = userService.getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (user.getAccountBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("账户余额不足");
        }

        BigDecimal fee = amount.multiply(new BigDecimal("0.02")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal actualAmount = amount.subtract(fee).setScale(2, RoundingMode.HALF_UP);
        BigDecimal before = user.getAccountBalance();
        BigDecimal after = before.subtract(amount);

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setUserId(userId);
        withdrawal.setWithdrawalNo("WD" + IdUtil.getSnowflakeNextIdStr());
        withdrawal.setAmount(amount);
        withdrawal.setFee(fee);
        withdrawal.setActualAmount(actualAmount);
        withdrawal.setWithdrawType(withdrawType);
        withdrawal.setAccountName(accountName);
        withdrawal.setAccountNo(accountNo);
        withdrawal.setStatus(0);
        save(withdrawal);

        paymentService.addBalanceRecord(userId, "WITHDRAW", amount.negate(), before, after, null, "申请提现");
        userService.updateAccountBalance(userId, amount.negate());
        return withdrawal;
    }

    public List<Withdrawal> getUserWithdrawals(Long userId) {
        LambdaQueryWrapper<Withdrawal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Withdrawal::getUserId, userId)
                .orderByDesc(Withdrawal::getCreateTime);
        return list(wrapper);
    }
}
