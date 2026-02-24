package com.bike.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.bike.common.BusinessException;
import com.bike.entity.*;
import com.bike.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Service
public class RideService {

    @Autowired
    private RideOrderMapper rideOrderMapper;
    @Autowired
    private BikeMapper bikeMapper;
    @Autowired
    private StationMapper stationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PricingRuleMapper pricingRuleMapper;
    @Autowired
    private WalletRecordMapper walletRecordMapper;
    @Autowired
    private CreditRecordMapper creditRecordMapper;

    @Transactional
    public RideOrder startRide(Long userId, Long bikeId, Long stationId) {
        User user = userMapper.findById(userId);
        if (user.getDepositPaid() != 1) {
            throw new BusinessException("请先缴纳押金");
        }
        if (user.getCreditScore() < 60) {
            throw new BusinessException("信用分不足，无法骑行");
        }
        RideOrder current = rideOrderMapper.findCurrentByUserId(userId);
        if (current != null) {
            throw new BusinessException("您有未完成的骑行订单");
        }
        Bike bike = bikeMapper.findById(bikeId);
        if (bike == null || bike.getStatus() != 1) {
            throw new BusinessException("该单车不可用");
        }
        bikeMapper.updateStatus(bikeId, 2);
        stationMapper.updateCurrentCount(stationId, -1);
        RideOrder order = new RideOrder();
        order.setOrderNo("RD" + DateUtil.format(new Date(), "yyyyMMdd") + IdUtil.getSnowflakeNextIdStr().substring(10));
        order.setUserId(userId);
        order.setBikeId(bikeId);
        order.setStartStationId(stationId);
        order.setStartTime(new Date());
        order.setStatus(1);
        order.setPayStatus(0);
        rideOrderMapper.insert(order);
        return rideOrderMapper.findById(order.getId());
    }

    @Transactional
    public RideOrder endRide(Long userId, Long endStationId) {
        RideOrder order = rideOrderMapper.findCurrentByUserId(userId);
        if (order == null) {
            throw new BusinessException("没有进行中的骑行");
        }
        Date endTime = new Date();
        long minutes = (endTime.getTime() - order.getStartTime().getTime()) / 60000;
        if (minutes < 1) minutes = 1;
        Bike bike = bikeMapper.findById(order.getBikeId());
        BigDecimal amount = calculateFee(bike.getType(), (int) minutes);
        User user = userMapper.findById(userId);
        if (user.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("余额不足，请先充值");
        }
        order.setEndStationId(endStationId);
        order.setEndTime(endTime);
        order.setDuration((int) minutes);
        order.setDistance(BigDecimal.valueOf(minutes * 0.15).setScale(2, RoundingMode.HALF_UP));
        order.setAmount(amount);
        order.setStatus(3);
        order.setPayStatus(1);
        rideOrderMapper.update(order);
        userMapper.updateBalance(userId, amount.negate());
        WalletRecord walletRecord = new WalletRecord();
        walletRecord.setUserId(userId);
        walletRecord.setType(2);
        walletRecord.setAmount(amount.negate());
        walletRecord.setBalanceAfter(user.getBalance().subtract(amount));
        walletRecord.setDescription("骑行扣费-" + order.getOrderNo());
        walletRecordMapper.insert(walletRecord);
        bikeMapper.updateStatus(order.getBikeId(), 1);
        bikeMapper.updateStation(order.getBikeId(), endStationId);
        stationMapper.updateCurrentCount(endStationId, 1);
        int newScore = user.getCreditScore() + 2;
        userMapper.updateCreditScore(userId, newScore);
        CreditRecord creditRecord = new CreditRecord();
        creditRecord.setUserId(userId);
        creditRecord.setType(1);
        creditRecord.setScoreChange(2);
        creditRecord.setScoreAfter(newScore);
        creditRecord.setDescription("正常还车奖励");
        creditRecordMapper.insert(creditRecord);
        return rideOrderMapper.findById(order.getId());
    }

    private BigDecimal calculateFee(Integer bikeType, int minutes) {
        PricingRule rule = pricingRuleMapper.findByBikeType(bikeType);
        if (rule == null) {
            return BigDecimal.valueOf(1.5);
        }
        BigDecimal fee;
        if (minutes <= rule.getBaseDuration()) {
            fee = rule.getBasePrice();
        } else {
            int extraMinutes = minutes - rule.getBaseDuration();
            int extraPeriods = (int) Math.ceil(extraMinutes / 30.0);
            fee = rule.getBasePrice().add(rule.getExtraPrice().multiply(BigDecimal.valueOf(extraPeriods)));
        }
        if (fee.compareTo(rule.getDailyCap()) > 0) {
            fee = rule.getDailyCap();
        }
        return fee.setScale(2, RoundingMode.HALF_UP);
    }

    public RideOrder getCurrent(Long userId) {
        return rideOrderMapper.findCurrentByUserId(userId);
    }

    public PageInfo<RideOrder> getMyList(Long userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(rideOrderMapper.findByUserId(userId));
    }

    public PageInfo<RideOrder> getAllList(Integer pageNum, Integer pageSize, String orderNo, Long userId, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(rideOrderMapper.findList(orderNo, userId, status));
    }

    public RideOrder getById(Long id) {
        return rideOrderMapper.findById(id);
    }
}
