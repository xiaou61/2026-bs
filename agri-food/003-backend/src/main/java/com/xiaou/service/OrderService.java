package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService extends IService<Order> {
    Order createOrder(Long userId, List<Map<String, Object>> items, Map<String, String> shippingInfo);
    void updateOrderStatus(Long orderId, String status, Long userId);
    IPage<Order> getOrdersByBuyer(Long buyerId, Integer page, Integer size);
    IPage<Order> getOrdersByFarmer(Long farmerId, Integer page, Integer size);
    Map<String, Object> getOrderDetail(Long orderId, Long userId);
}

