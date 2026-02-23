package com.ticket.service;

import cn.hutool.core.util.IdUtil;
import com.ticket.common.BusinessException;
import com.ticket.entity.Order;
import com.ticket.entity.Payment;
import com.ticket.entity.User;
import com.ticket.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Transactional
    public void createPayment(Long orderId, String payType) {
        Order order = orderService.getOrderById(orderId);
        
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setPayNo("PAY" + IdUtil.getSnowflakeNextIdStr());
        payment.setPayType(payType);
        payment.setPayAmount(order.getPayAmount());
        payment.setStatus("pending");

        paymentMapper.insert(payment);
    }

    @Transactional
    public void balancePay(Long orderId, Long userId) {
        Order order = orderService.getOrderById(orderId);
        if (!"unpaid".equals(order.getStatus())) {
            throw new BusinessException("订单状态不允许支付");
        }

        User user = userService.getUserInfo(userId);
        if (user.getBalance().compareTo(order.getPayAmount()) < 0) {
            throw new BusinessException("余额不足");
        }

        user.setBalance(user.getBalance().subtract(order.getPayAmount()));
        userService.updateUser(user);

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setPayNo("PAY" + IdUtil.getSnowflakeNextIdStr());
        payment.setPayType("balance");
        payment.setPayAmount(order.getPayAmount());
        payment.setStatus("success");
        payment.setPayTime(LocalDateTime.now());
        paymentMapper.insert(payment);

        order.setStatus("paid");
        order.setPayTime(LocalDateTime.now());
        orderService.getOrderById(orderId);

        ticketService.generateTickets(orderId);
    }

    public void recharge(Long userId, BigDecimal amount) {
        userService.recharge(userId, amount);
    }
}
