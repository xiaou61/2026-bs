package com.xiaou.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.Bill;
import com.xiaou.entity.Payment;
import com.xiaou.mapper.BillMapper;
import com.xiaou.mapper.PaymentMapper;
import com.xiaou.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {

    private final PaymentMapper paymentMapper;

    @Override
    public IPage<Bill> pageList(Long elderId, Integer current, Integer size, Integer status) {
        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<>();
        if (elderId != null) {
            wrapper.eq(Bill::getElderId, elderId);
        }
        if (status != null) {
            wrapper.eq(Bill::getStatus, status);
        }
        wrapper.orderByDesc(Bill::getBillMonth);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    @Transactional
    public void pay(Long billId, BigDecimal amount, Integer payMethod, Long operatorId) {
        Bill bill = getById(billId);
        if (bill == null) {
            throw new BusinessException("账单不存在");
        }
        if (bill.getStatus() == 2) {
            throw new BusinessException("账单已支付");
        }
        BigDecimal newPaid = bill.getPaidAmount().add(amount);
        if (newPaid.compareTo(bill.getTotalAmount()) > 0) {
            throw new BusinessException("支付金额超出账单金额");
        }

        Payment payment = new Payment();
        payment.setPaymentNo("P" + IdUtil.getSnowflakeNextIdStr());
        payment.setBillId(billId);
        payment.setElderId(bill.getElderId());
        payment.setAmount(amount);
        payment.setPayMethod(payMethod);
        payment.setPayTime(LocalDateTime.now());
        payment.setOperatorId(operatorId);
        paymentMapper.insert(payment);

        bill.setPaidAmount(newPaid);
        if (newPaid.compareTo(bill.getTotalAmount()) >= 0) {
            bill.setStatus(2);
        } else {
            bill.setStatus(1);
        }
        updateById(bill);
    }
}
