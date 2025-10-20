package com.xiaou.express.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.express.common.BusinessException;
import com.xiaou.express.common.Constants;
import com.xiaou.express.dto.OrderCreateRequest;
import com.xiaou.express.entity.Orders;
import com.xiaou.express.entity.User;
import com.xiaou.express.mapper.OrdersMapper;
import com.xiaou.express.mapper.UserMapper;
import com.xiaou.express.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WalletService walletService;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public void createOrder(Long userId, OrderCreateRequest request) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        walletService.freezeAmount(userId, request.getFee(), "发布订单冻结");

        Orders order = new Orders();
        BeanUtils.copyProperties(request, order);
        order.setOrderNo(generateOrderNo());
        order.setPublisherId(userId);
        order.setStatus(Constants.OrderStatus.PENDING);
        ordersMapper.insert(order);

        User publisher = userMapper.selectById(userId);
        publisher.setTotalOrders(publisher.getTotalOrders() + 1);
        userMapper.updateById(publisher);
    }

    private String generateOrderNo() {
        return "EXP" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }

    public Page<OrderVO> getOrderSquare(int pageNum, int pageSize) {
        Page<Orders> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getStatus, Constants.OrderStatus.PENDING)
                .orderByDesc(Orders::getCreateTime);
        Page<Orders> orderPage = ordersMapper.selectPage(page, wrapper);

        Page<OrderVO> voPage = new Page<>();
        voPage.setCurrent(orderPage.getCurrent());
        voPage.setSize(orderPage.getSize());
        voPage.setTotal(orderPage.getTotal());
        voPage.setRecords(orderPage.getRecords().stream().map(this::convertToVO).toList());
        return voPage;
    }

    @Transactional
    public void acceptOrder(Long userId, Long orderId) {
        Orders order = ordersMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        if (order.getStatus() != Constants.OrderStatus.PENDING) {
            throw new BusinessException("订单状态不允许接单");
        }

        User user = userMapper.selectById(userId);
        if (user.getCreditScore() < Constants.MIN_CREDIT_SCORE) {
            throw new BusinessException("信用分不足，无法接单");
        }

        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getTakerId, userId)
                .in(Orders::getStatus, Constants.OrderStatus.ACCEPTED, Constants.OrderStatus.PICKED_UP, Constants.OrderStatus.DELIVERING);
        long activeOrders = ordersMapper.selectCount(wrapper);
        if (activeOrders >= Constants.MAX_ACTIVE_ORDERS) {
            throw new BusinessException("最多同时接" + Constants.MAX_ACTIVE_ORDERS + "单");
        }

        order.setTakerId(userId);
        order.setStatus(Constants.OrderStatus.ACCEPTED);
        order.setAcceptTime(LocalDateTime.now());
        ordersMapper.updateById(order);

        notificationService.sendNotification(order.getPublisherId(), Constants.NotificationType.ORDER,
                "订单已被接单", "您的订单" + order.getOrderNo() + "已被接单", "order", orderId);
    }

    @Transactional
    public void uploadPickupImage(Long userId, Long orderId, String image) {
        Orders order = ordersMapper.selectById(orderId);
        if (order == null || !order.getTakerId().equals(userId)) {
            throw new BusinessException("无权操作");
        }

        if (order.getStatus() != Constants.OrderStatus.ACCEPTED) {
            throw new BusinessException("订单状态不正确");
        }

        order.setPickupImage(image);
        order.setPickupTime(LocalDateTime.now());
        order.setStatus(Constants.OrderStatus.PICKED_UP);
        ordersMapper.updateById(order);

        notificationService.sendNotification(order.getPublisherId(), Constants.NotificationType.ORDER,
                "快递已取件", "您的订单" + order.getOrderNo() + "已取件，正在配送中", "order", orderId);
    }

    @Transactional
    public void uploadDeliveryImage(Long userId, Long orderId, String image) {
        Orders order = ordersMapper.selectById(orderId);
        if (order == null || !order.getTakerId().equals(userId)) {
            throw new BusinessException("无权操作");
        }

        if (order.getStatus() != Constants.OrderStatus.PICKED_UP) {
            throw new BusinessException("订单状态不正确");
        }

        order.setDeliveryImage(image);
        order.setDeliveryTime(LocalDateTime.now());
        order.setStatus(Constants.OrderStatus.DELIVERING);
        ordersMapper.updateById(order);

        notificationService.sendNotification(order.getPublisherId(), Constants.NotificationType.ORDER,
                "快递已送达", "您的订单" + order.getOrderNo() + "已送达，请确认收货", "order", orderId);
    }

    @Transactional
    public void confirmOrder(Long userId, Long orderId) {
        Orders order = ordersMapper.selectById(orderId);
        if (order == null || !order.getPublisherId().equals(userId)) {
            throw new BusinessException("无权操作");
        }

        if (order.getStatus() != Constants.OrderStatus.DELIVERING) {
            throw new BusinessException("订单状态不正确");
        }

        walletService.unfreezeAndTransfer(order.getPublisherId(), order.getTakerId(), order.getFee(), "订单完成", orderId);

        order.setStatus(Constants.OrderStatus.COMPLETED);
        order.setFinishTime(LocalDateTime.now());
        ordersMapper.updateById(order);

        User taker = userMapper.selectById(order.getTakerId());
        taker.setTotalTakes(taker.getTotalTakes() + 1);
        userMapper.updateById(taker);

        notificationService.sendNotification(order.getTakerId(), Constants.NotificationType.ORDER,
                "订单已完成", "订单" + order.getOrderNo() + "已完成，获得收入" + order.getFee() + "元", "order", orderId);
    }

    @Transactional
    public void cancelOrder(Long userId, Long orderId, String reason) {
        Orders order = ordersMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        if (!order.getPublisherId().equals(userId) && !order.getTakerId().equals(userId)) {
            throw new BusinessException("无权操作");
        }

        if (order.getStatus() >= Constants.OrderStatus.COMPLETED) {
            throw new BusinessException("订单已完成或已取消");
        }

        walletService.refund(order.getPublisherId(), order.getFee(), "订单取消退款", orderId);

        order.setStatus(Constants.OrderStatus.CANCELLED);
        order.setCancelReason(reason);
        ordersMapper.updateById(order);
    }

    public Page<OrderVO> getMyPublishedOrders(Long userId, int pageNum, int pageSize, Integer status) {
        Page<Orders> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getPublisherId, userId);
        if (status != null) {
            wrapper.eq(Orders::getStatus, status);
        }
        wrapper.orderByDesc(Orders::getCreateTime);
        Page<Orders> orderPage = ordersMapper.selectPage(page, wrapper);

        Page<OrderVO> voPage = new Page<>();
        voPage.setCurrent(orderPage.getCurrent());
        voPage.setSize(orderPage.getSize());
        voPage.setTotal(orderPage.getTotal());
        voPage.setRecords(orderPage.getRecords().stream().map(this::convertToVO).toList());
        return voPage;
    }

    public Page<OrderVO> getMyAcceptedOrders(Long userId, int pageNum, int pageSize, Integer status) {
        Page<Orders> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getTakerId, userId);
        if (status != null) {
            wrapper.eq(Orders::getStatus, status);
        }
        wrapper.orderByDesc(Orders::getCreateTime);
        Page<Orders> orderPage = ordersMapper.selectPage(page, wrapper);

        Page<OrderVO> voPage = new Page<>();
        voPage.setCurrent(orderPage.getCurrent());
        voPage.setSize(orderPage.getSize());
        voPage.setTotal(orderPage.getTotal());
        voPage.setRecords(orderPage.getRecords().stream().map(this::convertToVO).toList());
        return voPage;
    }

    public OrderVO getOrderDetail(Long orderId) {
        Orders order = ordersMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        return convertToVO(order);
    }

    private OrderVO convertToVO(Orders order) {
        OrderVO vo = new OrderVO();
        BeanUtils.copyProperties(order, vo);

        User publisher = userMapper.selectById(order.getPublisherId());
        if (publisher != null) {
            vo.setPublisherName(publisher.getRealName());
        }

        if (order.getTakerId() != null) {
            User taker = userMapper.selectById(order.getTakerId());
            if (taker != null) {
                vo.setTakerName(taker.getRealName());
            }
        }

        vo.setStatusText(getStatusText(order.getStatus()));
        return vo;
    }

    private String getStatusText(Integer status) {
        return switch (status) {
            case Constants.OrderStatus.PENDING -> "待接单";
            case Constants.OrderStatus.ACCEPTED -> "待取件";
            case Constants.OrderStatus.PICKED_UP -> "配送中";
            case Constants.OrderStatus.DELIVERING -> "待确认";
            case Constants.OrderStatus.COMPLETED -> "已完成";
            case Constants.OrderStatus.CANCELLED -> "已取消";
            case Constants.OrderStatus.REFUNDED -> "已退款";
            default -> "未知";
        };
    }
}

