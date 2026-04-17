package com.xiaou.ordering.service;

import com.xiaou.ordering.entity.Merchant;
import com.xiaou.ordering.entity.Orders;
import com.xiaou.ordering.mapper.DishMapper;
import com.xiaou.ordering.mapper.MerchantMapper;
import com.xiaou.ordering.mapper.OrdersMapper;
import com.xiaou.ordering.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final UserMapper userMapper;
    private final MerchantMapper merchantMapper;
    private final OrdersMapper ordersMapper;
    private final DishMapper dishMapper;

    public AdminService(UserMapper userMapper, MerchantMapper merchantMapper, OrdersMapper ordersMapper, DishMapper dishMapper) {
        this.userMapper = userMapper;
        this.merchantMapper = merchantMapper;
        this.ordersMapper = ordersMapper;
        this.dishMapper = dishMapper;
    }

    public Map<String, Object> getStatistics() {
        List<Orders> orders = ordersMapper.selectList(null);
        List<Merchant> merchants = merchantMapper.selectList(null);

        BigDecimal totalAmount = orders.stream()
                .filter(order -> order.getStatus() != null && order.getStatus() != 5 && order.getStatus() != 6)
                .map(Orders::getPayAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> data = new HashMap<>();
        data.put("userCount", userMapper.selectCount(null));
        data.put("merchantCount", merchants.size());
        data.put("activeMerchantCount", merchants.stream().filter(merchant -> merchant.getStatus() != null && merchant.getStatus() == 1).count());
        data.put("pendingAuditCount", merchants.stream().filter(merchant -> merchant.getAuditStatus() != null && merchant.getAuditStatus() == 0).count());
        data.put("dishCount", dishMapper.selectCount(null));
        data.put("orderCount", orders.size());
        data.put("completedOrderCount", orders.stream().filter(order -> order.getStatus() != null && order.getStatus() == 4).count());
        data.put("totalAmount", totalAmount);
        return data;
    }
}
