package com.xiaou.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.dto.OrderDTO;
import com.xiaou.entity.RecycleOrder;

import java.util.List;
import java.util.Map;

public interface RecycleOrderService extends IService<RecycleOrder> {
    void createOrder(OrderDTO dto, Long userId);
    IPage<RecycleOrder> pageByUser(Long userId, Integer current, Integer size);
    IPage<RecycleOrder> pageByCollector(Long collectorId, Integer current, Integer size, Integer status);
    IPage<RecycleOrder> pageList(Integer current, Integer size, Integer status, Long communityId);
    void acceptOrder(Long orderId, Long collectorId);
    void completeOrder(Long orderId, List<OrderDTO.OrderDetailDTO> details);
    void cancelOrder(Long orderId, String reason);
    Map<String, Object> getStatistics();
}
