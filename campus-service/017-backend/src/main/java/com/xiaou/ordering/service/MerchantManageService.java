package com.xiaou.ordering.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.ordering.entity.Merchant;
import com.xiaou.ordering.entity.Orders;
import com.xiaou.ordering.mapper.MerchantMapper;
import com.xiaou.ordering.mapper.OrdersMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MerchantManageService {

    private final MerchantMapper merchantMapper;
    private final OrdersMapper ordersMapper;

    public MerchantManageService(MerchantMapper merchantMapper, OrdersMapper ordersMapper) {
        this.merchantMapper = merchantMapper;
        this.ordersMapper = ordersMapper;
    }

    public Map<String, Object> getDashboard(Long merchantId) {
        Merchant merchant = merchantMapper.selectById(merchantId);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getMerchantId, merchantId);
        List<Orders> orders = ordersMapper.selectList(wrapper);

        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        BigDecimal todayRevenue = orders.stream()
                .filter(order -> order.getCreateTime() != null && !order.getCreateTime().isBefore(todayStart))
                .filter(order -> order.getStatus() != null && order.getStatus() != 5 && order.getStatus() != 6)
                .map(Orders::getPayAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> data = new HashMap<>();
        data.put("merchant", merchant);
        data.put("totalOrders", orders.size());
        data.put("pendingOrders", orders.stream().filter(order -> order.getStatus() != null && order.getStatus() == 1).count());
        data.put("preparingOrders", orders.stream().filter(order -> order.getStatus() != null && order.getStatus() == 2).count());
        data.put("readyOrders", orders.stream().filter(order -> order.getStatus() != null && order.getStatus() == 3).count());
        data.put("completedOrders", orders.stream().filter(order -> order.getStatus() != null && order.getStatus() == 4).count());
        data.put("todayRevenue", todayRevenue);
        return data;
    }
}
