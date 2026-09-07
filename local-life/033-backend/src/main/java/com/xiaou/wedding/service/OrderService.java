package com.xiaou.wedding.service;

import com.xiaou.wedding.entity.Order;
import java.util.List;

public interface OrderService {
    List<Order> list(Long customerId, String status);

    Order detail(Long id);

    void create(Order order);

    void updateStatus(Long id, String status, java.math.BigDecimal paidAmount);
}
