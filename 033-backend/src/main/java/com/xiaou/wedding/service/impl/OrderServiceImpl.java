package com.xiaou.wedding.service.impl;

import cn.hutool.core.util.IdUtil;
import com.xiaou.wedding.entity.Order;
import com.xiaou.wedding.exception.BusinessException;
import com.xiaou.wedding.mapper.OrderMapper;
import com.xiaou.wedding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> list(Long customerId, String status) {
        return orderMapper.list(customerId, status);
    }

    @Override
    public Order detail(Long id) {
        Order order = orderMapper.findById(id);
        if (order == null) {
            throw new BusinessException("Order not found");
        }
        return order;
    }

    @Override
    public void create(Order order) {
        order.setOrderNo(IdUtil.getSnowflakeNextIdStr());
        order.setStatus(order.getStatus() == null ? "PENDING" : order.getStatus());
        order.setPaidAmount(order.getPaidAmount() == null ? BigDecimal.ZERO : order.getPaidAmount());
        orderMapper.insert(order);
    }

    @Override
    public void updateStatus(Long id, String status, BigDecimal paidAmount) {
        Order order = orderMapper.findById(id);
        if (order == null) {
            throw new BusinessException("Order not found");
        }
        order.setStatus(status);
        if (paidAmount != null) {
            order.setPaidAmount(paidAmount);
        }
        orderMapper.update(order);
    }
}
