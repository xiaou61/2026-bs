package com.xiaou.artist.service.impl;

import com.xiaou.artist.entity.Order;
import com.xiaou.artist.mapper.OrderMapper;
import com.xiaou.artist.mapper.UserMapper;
import com.xiaou.artist.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public Order createOrder(Order order) {
        // 生成订单号
        String orderNo = "ORD" + System.currentTimeMillis();
        order.setOrderNo(orderNo);
        if (order.getStatus() == null) {
            order.setStatus("PENDING_DEPOSIT");
        }
        if (order.getMaxRevise() ==null) {
            order.setMaxRevise(2);
        }
        if (order.getCommissionRate() == null) {
            order.setCommissionRate(new java.math.BigDecimal("8.00"));
        }
        orderMapper.insert(order);
        return order;
    }
    
    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderMapper.selectByUserId(userId);
    }
    
    @Override
    public List<Order> getOrdersByArtistId(Long artistId) {
        return orderMapper.selectByArtistId(artistId);
    }
    
    @Override
    public List<Order> getAllOrders() {
        return orderMapper.selectAll();
    }
    
    @Override
    public Order getOrderById(Long id) {
        return orderMapper.selectById(id);
    }
    
    @Override
    public boolean payDeposit(Long id) {
        Order order = orderMapper.selectById(id);
        // 扣除用户余额
        userMapper.updateBalance(order.getUserId(), order.getDeposit().negate());
        // 更新订单状态
        orderMapper.updateStatus(id, "IN_PRODUCTION");
        return true;
    }
    
    @Override
    public boolean submitDraft(Long id, String url) {
        orderMapper.updateDraftUrl(id, url);
        orderMapper.updateStatus(id, "DRAFT_SUBMITTED");
        return true;
    }
    
    @Override
    public boolean confirmDraft(Long id) {
        orderMapper.updateStatus(id, "IN_PRODUCTION");
        return true;
    }
    
    @Override
    public boolean requestRevise(Long id) {
        Order order = orderMapper.selectById(id);
        if (order.getReviseCount() >= order.getMaxRevise()) {
            throw new RuntimeException("已达到最大修改次数");
        }
        orderMapper.incrementReviseCount(id);
        orderMapper.updateStatus(id, "REVISING");
        return true;
    }
    
    @Override
    public boolean submitFinal(Long id, String url) {
        orderMapper.updateFinalUrl(id, url);
        orderMapper.updateStatus(id, "PENDING_FINAL_PAYMENT");
        return true;
    }
    
    @Override
    public boolean payFinalPayment(Long id) {
        Order order = orderMapper.selectById(id);
        // 扣除用户余额
        userMapper.updateBalance(order.getUserId(), order.getFinalPayment().negate());
        // 计算画师收益（扣除平台抽成）
        var commission = order.getTotalPrice().multiply(order.getCommissionRate()).divide(new java.math.BigDecimal("100"));
        var artistIncome = order.getTotalPrice().subtract(commission);
        // 增加画师余额
        userMapper.updateBalance(order.getUserId(), artistIncome);
        // 更新订单状态
        orderMapper.updateStatus(id, "COMPLETED");
        return true;
    }
    
    @Override
    public boolean completeOrder(Long id) {
        return orderMapper.updateStatus(id, "COMPLETED") > 0;
    }
}
