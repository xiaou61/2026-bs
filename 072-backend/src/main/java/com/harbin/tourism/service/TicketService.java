package com.harbin.tourism.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.BusinessException;
import com.harbin.tourism.entity.TicketOrder;
import com.harbin.tourism.entity.TicketType;
import com.harbin.tourism.entity.User;
import com.harbin.tourism.mapper.TicketOrderMapper;
import com.harbin.tourism.mapper.TicketTypeMapper;
import com.harbin.tourism.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketTypeMapper ticketTypeMapper;

    @Autowired
    private TicketOrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    public List<TicketType> getTicketTypes(Long spotId) {
        return ticketTypeMapper.selectList(new LambdaQueryWrapper<TicketType>()
                .eq(TicketType::getSpotId, spotId)
                .eq(TicketType::getStatus, 1));
    }

    public void saveTicketType(TicketType ticketType) {
        ticketType.setStatus(1);
        ticketTypeMapper.insert(ticketType);
    }

    public void updateTicketType(TicketType ticketType) {
        ticketTypeMapper.updateById(ticketType);
    }

    public void deleteTicketType(Long id) {
        ticketTypeMapper.deleteById(id);
    }

    @Transactional
    public TicketOrder createOrder(Long userId, Long spotId, Long ticketTypeId, String ticketDate, Integer quantity) {
        TicketType ticketType = ticketTypeMapper.selectById(ticketTypeId);
        if (ticketType == null) {
            throw new BusinessException("票种不存在");
        }
        if (ticketType.getStock() < quantity) {
            throw new BusinessException("库存不足");
        }
        if (quantity > ticketType.getMaxPerOrder()) {
            throw new BusinessException("超过每单限购数量");
        }
        BigDecimal totalPrice = ticketType.getPrice().multiply(new BigDecimal(quantity));
        User user = userMapper.selectById(userId);
        if (user.getBalance().compareTo(totalPrice) < 0) {
            throw new BusinessException("余额不足，请先充值");
        }
        ticketType.setStock(ticketType.getStock() - quantity);
        ticketTypeMapper.updateById(ticketType);
        user.setBalance(user.getBalance().subtract(totalPrice));
        userMapper.updateById(user);
        TicketOrder order = new TicketOrder();
        order.setOrderNo(IdUtil.simpleUUID());
        order.setUserId(userId);
        order.setSpotId(spotId);
        order.setTicketTypeId(ticketTypeId);
        order.setTicketDate(java.time.LocalDate.parse(ticketDate));
        order.setQuantity(quantity);
        order.setTotalPrice(totalPrice);
        order.setStatus("paid");
        orderMapper.insert(order);
        return order;
    }

    public Page<TicketOrder> userOrders(Long userId, Integer pageNum, Integer pageSize, String status) {
        LambdaQueryWrapper<TicketOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TicketOrder::getUserId, userId);
        if (StrUtil.isNotBlank(status)) {
            wrapper.eq(TicketOrder::getStatus, status);
        }
        wrapper.orderByDesc(TicketOrder::getCreatedAt);
        return orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Page<TicketOrder> allOrders(Integer pageNum, Integer pageSize, String status, String orderNo) {
        LambdaQueryWrapper<TicketOrder> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(status)) {
            wrapper.eq(TicketOrder::getStatus, status);
        }
        if (StrUtil.isNotBlank(orderNo)) {
            wrapper.like(TicketOrder::getOrderNo, orderNo);
        }
        wrapper.orderByDesc(TicketOrder::getCreatedAt);
        return orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public TicketOrder getOrderById(Long id) {
        return orderMapper.selectById(id);
    }

    @Transactional
    public void refund(Long orderId) {
        TicketOrder order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (!"paid".equals(order.getStatus())) {
            throw new BusinessException("订单状态不允许退款");
        }
        User user = userMapper.selectById(order.getUserId());
        user.setBalance(user.getBalance().add(order.getTotalPrice()));
        userMapper.updateById(user);
        TicketType ticketType = ticketTypeMapper.selectById(order.getTicketTypeId());
        ticketType.setStock(ticketType.getStock() + order.getQuantity());
        ticketTypeMapper.updateById(ticketType);
        order.setStatus("refunded");
        orderMapper.updateById(order);
    }

    public void updateOrderStatus(Long orderId, String status) {
        TicketOrder order = new TicketOrder();
        order.setId(orderId);
        order.setStatus(status);
        orderMapper.updateById(order);
    }

    public long orderCount() {
        return orderMapper.selectCount(null);
    }

    public BigDecimal totalIncome() {
        List<TicketOrder> orders = orderMapper.selectList(new LambdaQueryWrapper<TicketOrder>()
                .eq(TicketOrder::getStatus, "paid")
                .or().eq(TicketOrder::getStatus, "used"));
        return orders.stream()
                .map(TicketOrder::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
