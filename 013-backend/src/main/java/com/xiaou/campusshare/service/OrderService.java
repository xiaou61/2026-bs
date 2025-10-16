package com.xiaou.campusshare.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.campusshare.entity.OrderInfo;
import com.xiaou.campusshare.mapper.OrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService extends ServiceImpl<OrderInfoMapper, OrderInfo> {

    @Autowired
    private UserService userService;

    @Autowired
    private SharedItemService sharedItemService;

    @Autowired
    private IdleItemService idleItemService;

    @Autowired
    private SkillServiceService skillServiceService;

    @Transactional
    public OrderInfo createSharedOrder(Long userId, Long itemId, String orderType) {
        OrderInfo order = new OrderInfo();
        order.setOrderNo(IdUtil.getSnowflakeNextIdStr());
        order.setOrderType(orderType);
        order.setUserId(userId);
        order.setItemId(itemId);
        order.setStartTime(LocalDateTime.now());
        order.setOrderStatus(1);
        order.setPaymentStatus(1);
        order.setDepositStatus(1);
        
        save(order);
        sharedItemService.updateItemStatus(itemId, 1);
        sharedItemService.increaseUsageCount(itemId);
        
        return order;
    }

    @Transactional
    public boolean returnSharedItem(Long orderId) {
        OrderInfo order = getById(orderId);
        if (order == null || order.getOrderStatus() != 1) {
            return false;
        }

        order.setEndTime(LocalDateTime.now());
        order.setOrderStatus(4);
        order.setCompleteTime(LocalDateTime.now());

        long minutes = Duration.between(order.getStartTime(), order.getEndTime()).toMinutes();
        order.setActualDuration((int) minutes);

        updateById(order);
        sharedItemService.updateItemStatus(order.getItemId(), 0);
        
        return true;
    }

    public List<OrderInfo> getUserOrders(Long userId, Integer status) {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderInfo::getUserId, userId);
        if (status != null) {
            wrapper.eq(OrderInfo::getOrderStatus, status);
        }
        wrapper.orderByDesc(OrderInfo::getCreateTime);
        return list(wrapper);
    }

    public BigDecimal calculateFee(OrderInfo order, BigDecimal hourlyPrice, BigDecimal dailyMaxPrice) {
        int minutes = order.getActualDuration();
        int hours = (minutes + 59) / 60;
        
        BigDecimal fee = hourlyPrice.multiply(BigDecimal.valueOf(hours));
        if (dailyMaxPrice != null) {
            int days = hours / 24 + (hours % 24 > 0 ? 1 : 0);
            BigDecimal dailyFee = dailyMaxPrice.multiply(BigDecimal.valueOf(days));
            if (dailyFee.compareTo(fee) < 0) {
                fee = dailyFee;
            }
        }
        
        return fee;
    }
}

