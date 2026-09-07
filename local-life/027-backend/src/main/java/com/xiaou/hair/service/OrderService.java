package com.xiaou.hair.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.hair.entity.Appointment;
import com.xiaou.hair.entity.Order;
import com.xiaou.hair.entity.ServiceEntity;
import com.xiaou.hair.mapper.OrderMapper;
import com.xiaou.hair.mapper.ServiceMapper;
import com.xiaou.hair.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单服务类
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

//    @Autowired
//    private AppointmentMapper appointmentMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private UserService userService;

    /**
     * 根据预约创建订单
     */
    @Transactional
//    public Order createOrderFromAppointment(Long appointmentId) {
////        Appointment appointment = appointmentMapper.selectById(appointmentId);
////        if (appointment == null) {
////            throw new RuntimeException("预约不存在");
////        }
////
////        // 获取服务项目信息
////        ServiceEntity service = serviceMapper.selectById(appointment.getServiceId());
////        if (service == null) {
////            throw new RuntimeException("服务项目不存在");
////        }
////
////        // 获取会员折扣
////        BigDecimal discount = userService.getMemberDiscount(appointment.getUserId());
////        BigDecimal originalPrice = service.getPrice();
////        BigDecimal actualPrice = originalPrice.multiply(discount).setScale(2, BigDecimal.ROUND_HALF_UP);
////
////        // 创建订单
////        Order order = new Order();
////        order.setOrderNo("ORD" + IdUtil.getSnowflakeNextIdStr());
////        order.setAppointmentId(appointmentId);
////        order.setUserId(appointment.getUserId());
////        order.setStoreId(appointment.getStoreId());
////        order.setHairdresserId(appointment.getHairdresserId());
////        order.setServiceName(service.getName());
////        order.setOriginalPrice(originalPrice);
////        order.setDiscount(discount);
////        order.setActualPrice(actualPrice);
////        order.setPayStatus(0);
////
////        orderMapper.insert(order);
////        return order;
//    }

    /**
     * 订单列表
     */
    public Page<Order> getOrderList(Integer pageNum, Integer pageSize, Integer payStatus) {
        Long userId = UserContext.getUserId();
        Page<Order> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Order::getUserId, userId);

        if (payStatus != null) {
            wrapper.eq(Order::getPayStatus, payStatus);
        }

        wrapper.orderByDesc(Order::getCreatedAt);
        return orderMapper.selectPage(page, wrapper);
    }

    /**
     * 订单详情
     */
    public Order getOrderById(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        Long userId = UserContext.getUserId();
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("无权查看他人的订单");
        }

        return order;
    }

    /**
     * 订单支付
     */
    @Transactional
    public void payOrder(Long id, String payType) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        Long userId = UserContext.getUserId();
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("无权支付他人的订单");
        }

        if (order.getPayStatus() == 1) {
            throw new RuntimeException("订单已支付");
        }

        // 余额支付
        if ("余额支付".equals(payType)) {
            userService.deductBalance(userId, order.getActualPrice(), "支付订单" + order.getOrderNo());
        }

        // 更新订单状态
        order.setPayType(payType);
        order.setPayStatus(1);
        order.setPayTime(LocalDateTime.now());
        orderMapper.updateById(order);

        // 添加积分（消费1元=1积分）
        int points = order.getActualPrice().intValue();
        userService.addPoints(userId, points, "消费获得积分");

        // TODO: 发送支付成功通知
    }
}
