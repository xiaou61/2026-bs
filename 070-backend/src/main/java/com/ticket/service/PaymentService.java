package com.ticket.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.common.PageResult;
import com.ticket.dto.PaymentDTO;
import com.ticket.entity.Payment;
import com.ticket.entity.TicketOrder;
import com.ticket.entity.User;
import com.ticket.mapper.PaymentMapper;
import com.ticket.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PaymentService {

    @Resource
    private PaymentMapper paymentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Resource
    private OrderService orderService;

    public Payment create(Long userId, PaymentDTO dto) {
        if (dto == null || dto.getOrderId() == null) {
            throw new BusinessException("订单不能为空");
        }
        TicketOrder order = orderService.getPayableOrder(userId, dto.getOrderId());
        Payment exist = paymentMapper.selectOne(new LambdaQueryWrapper<Payment>()
                .eq(Payment::getOrderId, order.getId())
                .eq(Payment::getStatus, "WAIT")
                .last("limit 1"));
        if (exist != null) {
            return exist;
        }
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setPayNo("PAY" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + RandomUtil.randomNumbers(6));
        payment.setPayType(dto.getPayType() == null ? "BALANCE" : dto.getPayType().trim().toUpperCase());
        payment.setPayAmount(order.getPayAmount());
        payment.setStatus("WAIT");
        paymentMapper.insert(payment);
        return payment;
    }

    @Transactional(rollbackFor = Exception.class)
    public Payment balancePay(Long userId, Long orderId) {
        TicketOrder order = orderService.getPayableOrder(userId, orderId);
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        BigDecimal balance = user.getBalance() == null ? BigDecimal.ZERO : user.getBalance();
        if (balance.compareTo(order.getPayAmount()) < 0) {
            throw new BusinessException("余额不足");
        }
        userService.changeBalance(userId, order.getPayAmount().negate());
        Payment payment = paymentMapper.selectOne(new LambdaQueryWrapper<Payment>()
                .eq(Payment::getOrderId, order.getId())
                .orderByDesc(Payment::getId)
                .last("limit 1"));
        if (payment == null) {
            payment = new Payment();
            payment.setOrderId(order.getId());
            payment.setPayNo("PAY" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + RandomUtil.randomNumbers(6));
            payment.setPayType("BALANCE");
            payment.setPayAmount(order.getPayAmount());
            payment.setStatus("SUCCESS");
            payment.setPayTime(LocalDateTime.now());
            paymentMapper.insert(payment);
        } else {
            payment.setPayType("BALANCE");
            payment.setPayAmount(order.getPayAmount());
            payment.setStatus("SUCCESS");
            payment.setPayTime(LocalDateTime.now());
            paymentMapper.updateById(payment);
        }
        orderService.paySuccess(userId, orderId);
        return payment;
    }

    @Transactional(rollbackFor = Exception.class)
    public void recharge(Long userId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("充值金额必须大于0");
        }
        userService.changeBalance(userId, amount);
        Payment payment = new Payment();
        payment.setOrderId(null);
        payment.setPayNo("PAY" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + RandomUtil.randomNumbers(6));
        payment.setPayType("RECHARGE");
        payment.setPayAmount(amount);
        payment.setStatus("SUCCESS");
        payment.setPayTime(LocalDateTime.now());
        paymentMapper.insert(payment);
    }

    public PageResult<Payment> page(Integer pageNum, Integer pageSize, String status) {
        Page<Payment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<Payment>()
                .eq(status != null && !status.trim().isEmpty(), Payment::getStatus, status == null ? null : status.trim())
                .orderByDesc(Payment::getId);
        Page<Payment> resultPage = paymentMapper.selectPage(page, wrapper);
        PageResult<Payment> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }
}
