package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.OrderCreateDTO;
import com.xiaou.dto.OrderRateDTO;
import com.xiaou.vo.OrderDetailVO;
import com.xiaou.vo.OrderVO;

public interface OrderService {

    Long createOrder(Long userId, OrderCreateDTO createDTO);

    Page<OrderVO> getMySellOrders(Long userId, Integer current, Integer size);

    Page<OrderVO> getMyBuyOrders(Long userId, Integer current, Integer size);

    OrderDetailVO getOrderDetail(Long userId, Long orderId);

    void completeOrder(Long userId, Long orderId);

    void cancelOrder(Long userId, Long orderId);

    void rateOrder(Long userId, Long orderId, OrderRateDTO rateDTO);
}
