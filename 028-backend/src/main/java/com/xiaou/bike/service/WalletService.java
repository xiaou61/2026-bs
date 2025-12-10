package com.xiaou.bike.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.bike.common.BusinessException;
import com.xiaou.bike.dto.RechargeDTO;
import com.xiaou.bike.entity.RechargeRecord;
import com.xiaou.bike.entity.UserWallet;
import com.xiaou.bike.mapper.RechargeRecordMapper;
import com.xiaou.bike.mapper.UserWalletMapper;
import com.xiaou.bike.util.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 钱包服务
 */
@Service
@RequiredArgsConstructor
public class WalletService {

    private final UserWalletMapper userWalletMapper;
    private final RechargeRecordMapper rechargeRecordMapper;

    @Value("${billing.deposit:99.0}")
    private BigDecimal depositAmount;

    /**
     * 获取钱包信息
     */
    public UserWallet getWallet() {
        Long userId = UserContext.getUserId();
        LambdaQueryWrapper<UserWallet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserWallet::getUserId, userId);
        return userWalletMapper.selectOne(wrapper);
    }

    /**
     * 充值
     */
    @Transactional
    public void recharge(RechargeDTO dto) {
        Long userId = UserContext.getUserId();
        
        LambdaQueryWrapper<UserWallet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserWallet::getUserId, userId);
        UserWallet wallet = userWalletMapper.selectOne(wrapper);
        
        if (wallet == null) {
            throw BusinessException.of("钱包不存在");
        }

        // 模拟支付成功
        if (dto.getType() == 1) {
            // 余额充值
            wallet.setBalance(wallet.getBalance().add(dto.getAmount()));
        } else if (dto.getType() == 2) {
            // 押金缴纳
            if (wallet.getDepositStatus() == 1) {
                throw BusinessException.of("已缴纳押金");
            }
            wallet.setDeposit(depositAmount);
            wallet.setDepositStatus(1);
        }
        
        userWalletMapper.updateById(wallet);

        // 记录充值
        RechargeRecord record = new RechargeRecord();
        record.setUserId(userId);
        record.setAmount(dto.getType() == 1 ? dto.getAmount() : depositAmount);
        record.setType(dto.getType());
        record.setPayMethod(dto.getPayMethod());
        record.setStatus(1);
        record.setTransactionNo("TXN" + IdUtil.getSnowflakeNextIdStr());
        rechargeRecordMapper.insert(record);
    }

    /**
     * 申请退还押金
     */
    @Transactional
    public void refundDeposit() {
        Long userId = UserContext.getUserId();
        
        LambdaQueryWrapper<UserWallet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserWallet::getUserId, userId);
        UserWallet wallet = userWalletMapper.selectOne(wrapper);
        
        if (wallet == null || wallet.getDepositStatus() != 1) {
            throw BusinessException.of("无押金可退");
        }

        // 模拟退款成功
        wallet.setDeposit(BigDecimal.ZERO);
        wallet.setDepositStatus(3);
        userWalletMapper.updateById(wallet);
    }

    /**
     * 获取充值记录
     */
    public Page<RechargeRecord> getRechargeRecords(Integer page, Integer size) {
        Long userId = UserContext.getUserId();
        Page<RechargeRecord> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<RechargeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RechargeRecord::getUserId, userId);
        wrapper.orderByDesc(RechargeRecord::getCreateTime);
        return rechargeRecordMapper.selectPage(pageParam, wrapper);
    }
}
