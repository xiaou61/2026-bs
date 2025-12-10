package com.xiaou.bike.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.bike.common.BusinessException;
import com.xiaou.bike.dto.RentBikeDTO;
import com.xiaou.bike.dto.ReturnBikeDTO;
import com.xiaou.bike.entity.*;
import com.xiaou.bike.mapper.*;
import com.xiaou.bike.util.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 租借订单服务
 */
@Service
@RequiredArgsConstructor
public class RentalOrderService {

    private final RentalOrderMapper rentalOrderMapper;
    private final BicycleMapper bicycleMapper;
    private final UserMapper userMapper;
    private final UserWalletMapper userWalletMapper;
    private final StationService stationService;
    private final ConsumptionRecordMapper consumptionRecordMapper;
    private final CreditRecordMapper creditRecordMapper;

    @Value("${billing.price-per-30min:1.0}")
    private BigDecimal pricePer30Min;

    @Value("${billing.daily-cap:20.0}")
    private BigDecimal dailyCap;

    @Value("${billing.deposit:99.0}")
    private BigDecimal depositAmount;

    @Value("${billing.free-deposit-credit:600}")
    private Integer freeDepositCredit;

    @Value("${credit.normal-return:1}")
    private Integer normalReturnCredit;

    /**
     * 租车
     */
    @Transactional
    public RentalOrder rentBike(RentBikeDTO dto) {
        Long userId = UserContext.getUserId();
        
        // 检查用户是否有进行中的订单
        LambdaQueryWrapper<RentalOrder> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(RentalOrder::getUserId, userId);
        orderWrapper.eq(RentalOrder::getStatus, 0);
        if (rentalOrderMapper.selectCount(orderWrapper) > 0) {
            throw BusinessException.of("您有未完成的订单，请先还车");
        }

        // 检查用户押金状态
        User user = userMapper.selectById(userId);
        if (user.getCreditScore() < freeDepositCredit) {
            LambdaQueryWrapper<UserWallet> walletWrapper = new LambdaQueryWrapper<>();
            walletWrapper.eq(UserWallet::getUserId, userId);
            UserWallet wallet = userWalletMapper.selectOne(walletWrapper);
            if (wallet == null || wallet.getDepositStatus() != 1) {
                throw BusinessException.of("请先缴纳押金");
            }
        }

        // 查找车辆
        LambdaQueryWrapper<Bicycle> bikeWrapper = new LambdaQueryWrapper<>();
        bikeWrapper.eq(Bicycle::getBikeNo, dto.getBikeNo());
        Bicycle bicycle = bicycleMapper.selectOne(bikeWrapper);
        
        if (bicycle == null) {
            throw BusinessException.of("车辆不存在");
        }
        
        if (bicycle.getStatus() != 0) {
            throw BusinessException.of("车辆不可用");
        }

        // 更新车辆状态为使用中
        bicycle.setStatus(1);
        bicycle.setLongitude(dto.getLongitude());
        bicycle.setLatitude(dto.getLatitude());
        bicycleMapper.updateById(bicycle);

        // 减少停车点车辆数
        if (bicycle.getStationId() != null) {
            stationService.updateCount(bicycle.getStationId(), -1);
        }

        // 创建订单
        RentalOrder order = new RentalOrder();
        order.setOrderNo("ORD" + IdUtil.getSnowflakeNextIdStr());
        order.setUserId(userId);
        order.setBikeId(bicycle.getId());
        order.setStartStationId(bicycle.getStationId());
        order.setStartTime(LocalDateTime.now());
        order.setStatus(0);
        order.setPayStatus(0);
        rentalOrderMapper.insert(order);

        return order;
    }

    /**
     * 还车
     */
    @Transactional
    public RentalOrder returnBike(ReturnBikeDTO dto) {
        Long userId = UserContext.getUserId();
        
        // 查找订单
        RentalOrder order = rentalOrderMapper.selectById(dto.getOrderId());
        if (order == null) {
            throw BusinessException.of("订单不存在");
        }
        
        if (!order.getUserId().equals(userId)) {
            throw BusinessException.of("无权操作此订单");
        }
        
        if (order.getStatus() != 0) {
            throw BusinessException.of("订单状态异常");
        }

        // 计算费用
        LocalDateTime endTime = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(order.getStartTime(), endTime);
        BigDecimal amount = calculateAmount(minutes);

        // 扣除余额
        LambdaQueryWrapper<UserWallet> walletWrapper = new LambdaQueryWrapper<>();
        walletWrapper.eq(UserWallet::getUserId, userId);
        UserWallet wallet = userWalletMapper.selectOne(walletWrapper);
        
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw BusinessException.of("余额不足，请先充值");
        }
        
        userWalletMapper.deductBalance(userId, amount);

        // 更新订单
        order.setEndStationId(dto.getStationId());
        order.setEndTime(endTime);
        order.setDuration((int) minutes);
        order.setAmount(amount);
        order.setStatus(1);
        order.setPayStatus(1);
        order.setReturnPhoto(dto.getReturnPhoto());
        rentalOrderMapper.updateById(order);

        // 更新车辆状态
        Bicycle bicycle = bicycleMapper.selectById(order.getBikeId());
        bicycle.setStatus(0);
        bicycle.setStationId(dto.getStationId());
        bicycle.setLongitude(dto.getLongitude());
        bicycle.setLatitude(dto.getLatitude());
        bicycle.setTotalOrders(bicycle.getTotalOrders() + 1);
        bicycleMapper.updateById(bicycle);

        // 增加停车点车辆数
        stationService.updateCount(dto.getStationId(), 1);

        // 记录消费
        ConsumptionRecord record = new ConsumptionRecord();
        record.setUserId(userId);
        record.setOrderId(order.getId());
        record.setAmount(amount);
        record.setType(1);
        record.setBalanceAfter(wallet.getBalance().subtract(amount));
        record.setDescription("骑行消费 - 订单" + order.getOrderNo());
        consumptionRecordMapper.insert(record);

        // 增加信用分
        addCredit(userId, normalReturnCredit, 1, "正常还车", order.getId());

        return order;
    }

    /**
     * 计算费用
     */
    private BigDecimal calculateAmount(long minutes) {
        if (minutes <= 0) {
            return BigDecimal.ZERO;
        }
        
        // 每30分钟计费一次，不足30分钟按30分钟计算
        long units = (minutes + 29) / 30;
        BigDecimal amount = pricePer30Min.multiply(BigDecimal.valueOf(units));
        
        // 日封顶
        if (amount.compareTo(dailyCap) > 0) {
            amount = dailyCap;
        }
        
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 增加信用分
     */
    private void addCredit(Long userId, Integer value, Integer type, String reason, Long orderId) {
        User user = userMapper.selectById(userId);
        int newScore = user.getCreditScore() + value;
        
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setCreditScore(newScore);
        userMapper.updateById(updateUser);

        CreditRecord record = new CreditRecord();
        record.setUserId(userId);
        record.setChangeValue(value);
        record.setChangeType(type);
        record.setReason(reason);
        record.setOrderId(orderId);
        record.setScoreAfter(newScore);
        creditRecordMapper.insert(record);
    }

    /**
     * 获取当前进行中的订单
     */
    public RentalOrder getCurrentOrder() {
        Long userId = UserContext.getUserId();
        LambdaQueryWrapper<RentalOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RentalOrder::getUserId, userId);
        wrapper.eq(RentalOrder::getStatus, 0);
        return rentalOrderMapper.selectOne(wrapper);
    }

    /**
     * 获取用户订单列表
     */
    public Page<RentalOrder> getUserOrders(Integer page, Integer size, Integer status) {
        Long userId = UserContext.getUserId();
        Page<RentalOrder> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<RentalOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RentalOrder::getUserId, userId);
        
        if (status != null) {
            wrapper.eq(RentalOrder::getStatus, status);
        }
        
        wrapper.orderByDesc(RentalOrder::getCreateTime);
        return rentalOrderMapper.selectPage(pageParam, wrapper);
    }

    /**
     * 获取订单详情
     */
    public RentalOrder getOrderById(Long orderId) {
        return rentalOrderMapper.selectById(orderId);
    }

    /**
     * 分页查询订单（管理端）
     */
    public Page<RentalOrder> listOrders(Integer page, Integer size, String orderNo, Long userId, Integer status) {
        Page<RentalOrder> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<RentalOrder> wrapper = new LambdaQueryWrapper<>();
        
        if (orderNo != null && !orderNo.isEmpty()) {
            wrapper.like(RentalOrder::getOrderNo, orderNo);
        }
        
        if (userId != null) {
            wrapper.eq(RentalOrder::getUserId, userId);
        }
        
        if (status != null) {
            wrapper.eq(RentalOrder::getStatus, status);
        }
        
        wrapper.orderByDesc(RentalOrder::getCreateTime);
        return rentalOrderMapper.selectPage(pageParam, wrapper);
    }

    /**
     * 统计订单数量
     */
    public long countOrders(Integer status) {
        LambdaQueryWrapper<RentalOrder> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(RentalOrder::getStatus, status);
        }
        return rentalOrderMapper.selectCount(wrapper);
    }

    /**
     * 统计今日订单数量
     */
    public long countTodayOrders() {
        LambdaQueryWrapper<RentalOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(RentalOrder::getCreateTime, LocalDateTime.now().toLocalDate().atStartOfDay());
        return rentalOrderMapper.selectCount(wrapper);
    }
}
