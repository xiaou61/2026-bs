package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.dto.OrderDTO;
import com.xiaou.entity.*;
import com.xiaou.mapper.*;
import com.xiaou.service.RecycleOrderService;
import com.xiaou.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class RecycleOrderServiceImpl extends ServiceImpl<RecycleOrderMapper, RecycleOrder> implements RecycleOrderService {

    private final UserMapper userMapper;
    private final OrderDetailMapper orderDetailMapper;
    private final GarbageCategoryMapper categoryMapper;
    private final PointsRecordMapper pointsRecordMapper;
    private final UserService userService;

    @Override
    @Transactional
    public void createOrder(OrderDTO dto, Long userId) {
        User user = userMapper.selectById(userId);
        RecycleOrder order = new RecycleOrder();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setCommunityId(user.getCommunityId());
        order.setAddress(dto.getAddress());
        order.setContactName(dto.getContactName());
        order.setContactPhone(dto.getContactPhone());
        order.setAppointmentTime(dto.getAppointmentTime());
        order.setCategoryIds(dto.getCategoryIds());
        order.setEstimatedWeight(dto.getEstimatedWeight());
        order.setRemark(dto.getRemark());
        order.setStatus(0);
        save(order);
    }

    private String generateOrderNo() {
        return "GC" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + String.format("%04d", (int) (Math.random() * 10000));
    }

    @Override
    public IPage<RecycleOrder> pageByUser(Long userId, Integer current, Integer size) {
        return page(new Page<>(current, size),
                new LambdaQueryWrapper<RecycleOrder>()
                        .eq(RecycleOrder::getUserId, userId)
                        .orderByDesc(RecycleOrder::getCreateTime));
    }

    @Override
    public IPage<RecycleOrder> pageByCollector(Long collectorId, Integer current, Integer size, Integer status) {
        LambdaQueryWrapper<RecycleOrder> wrapper = new LambdaQueryWrapper<>();
        if (status != null && status == 0) {
            // 待接单，显示回收员所在小区的订单
            User collector = userMapper.selectById(collectorId);
            wrapper.eq(RecycleOrder::getCommunityId, collector.getCommunityId())
                    .eq(RecycleOrder::getStatus, 0);
        } else {
            wrapper.eq(RecycleOrder::getCollectorId, collectorId);
            if (status != null) {
                wrapper.eq(RecycleOrder::getStatus, status);
            }
        }
        wrapper.orderByDesc(RecycleOrder::getCreateTime);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    public IPage<RecycleOrder> pageList(Integer current, Integer size, Integer status, Long communityId) {
        LambdaQueryWrapper<RecycleOrder> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(RecycleOrder::getStatus, status);
        }
        if (communityId != null) {
            wrapper.eq(RecycleOrder::getCommunityId, communityId);
        }
        wrapper.orderByDesc(RecycleOrder::getCreateTime);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    @Transactional
    public void acceptOrder(Long orderId, Long collectorId) {
        RecycleOrder order = getById(orderId);
        if (order == null || order.getStatus() != 0) {
            throw new BusinessException("订单状态异常");
        }
        order.setCollectorId(collectorId);
        order.setStatus(1);
        updateById(order);
    }

    @Override
    @Transactional
    public void completeOrder(Long orderId, List<OrderDTO.OrderDetailDTO> details) {
        RecycleOrder order = getById(orderId);
        if (order == null || (order.getStatus() != 1 && order.getStatus() != 2)) {
            throw new BusinessException("订单状态异常");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        AtomicInteger totalPoints = new AtomicInteger(0);
        BigDecimal totalWeight = BigDecimal.ZERO;

        for (OrderDTO.OrderDetailDTO d : details) {
            GarbageCategory category = categoryMapper.selectById(d.getCategoryId());
            if (category == null) continue;

            BigDecimal amount = d.getWeight().multiply(category.getPrice());
            int points = d.getWeight().intValue() * category.getPointsRate();

            OrderDetail detail = new OrderDetail();
            detail.setOrderId(orderId);
            detail.setCategoryId(d.getCategoryId());
            detail.setWeight(d.getWeight());
            detail.setPrice(category.getPrice());
            detail.setAmount(amount);
            detail.setPoints(points);
            orderDetailMapper.insert(detail);

            totalAmount = totalAmount.add(amount);
            totalPoints.addAndGet(points);
            totalWeight = totalWeight.add(d.getWeight());
        }

        order.setActualWeight(totalWeight);
        order.setAmount(totalAmount);
        order.setPoints(totalPoints.get());
        order.setStatus(3);
        order.setCompleteTime(LocalDateTime.now());
        updateById(order);

        // 更新用户积分
        userService.updatePoints(order.getUserId(), totalPoints.get());

        // 记录积分
        User user = userMapper.selectById(order.getUserId());
        PointsRecord record = new PointsRecord();
        record.setUserId(order.getUserId());
        record.setPoints(totalPoints.get());
        record.setType(1);
        record.setOrderId(orderId);
        record.setDescription("垃圾回收获得积分");
        record.setBalance(user.getPoints());
        pointsRecordMapper.insert(record);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId, String reason) {
        RecycleOrder order = getById(orderId);
        if (order == null || order.getStatus() >= 3) {
            throw new BusinessException("订单无法取消");
        }
        order.setStatus(4);
        order.setCancelReason(reason);
        updateById(order);
    }

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalOrders", count());
        stats.put("completedOrders", count(new LambdaQueryWrapper<RecycleOrder>().eq(RecycleOrder::getStatus, 3)));
        stats.put("pendingOrders", count(new LambdaQueryWrapper<RecycleOrder>().eq(RecycleOrder::getStatus, 0)));
        return stats;
    }
}
