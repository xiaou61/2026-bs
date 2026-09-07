package com.milk.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.milk.entity.Payment;
import com.milk.mapper.PaymentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class PaymentService {

    @Resource
    private PaymentMapper paymentMapper;

    public BigDecimal totalAmount() {
        QueryWrapper<Payment> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        wrapper.select("IFNULL(SUM(amount),0) as amount");
        Payment payment = paymentMapper.selectOne(wrapper);
        return payment != null ? payment.getAmount() : BigDecimal.ZERO;
    }
}
