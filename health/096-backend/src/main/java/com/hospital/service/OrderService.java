package com.hospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hospital.common.BusinessException;
import com.hospital.entity.AppointmentRecord;
import com.hospital.entity.PaymentOrder;
import com.hospital.mapper.AppointmentRecordMapper;
import com.hospital.mapper.PaymentOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private PaymentOrderMapper paymentOrderMapper;

    @Autowired
    private AppointmentRecordMapper appointmentRecordMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    public PageInfo<PaymentOrder> page(String keyword, Integer status, Integer pageNum, Integer pageSize, String role) {
        authService.assertAdmin(role);
        PageHelper.startPage(pageNum, pageSize);
        List<PaymentOrder> list = paymentOrderMapper.selectPage(keyword, status);
        return new PageInfo<>(list);
    }

    public PageInfo<PaymentOrder> myPage(Long userId, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(paymentOrderMapper.selectMyPage(userId, status));
    }

    @Transactional
    public void pay(Long id, Long userId, String role) {
        authService.assertPatient(role);
        PaymentOrder order = paymentOrderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权限支付该订单");
        }
        if (order.getStatus() == null || order.getStatus() != 0) {
            throw new BusinessException("订单状态不可支付");
        }
        LocalDateTime now = LocalDateTime.now();
        paymentOrderMapper.updatePaid(id, 1, "在线支付", now);
        AppointmentRecord appointment = appointmentRecordMapper.selectById(order.getAppointmentId());
        if (appointment != null) {
            appointmentRecordMapper.updateStatus(appointment.getId(), 1, now, null);
        }
        operationLogService.record(userId, role, "订单", "支付", "支付挂号订单");
    }

    public java.math.BigDecimal sumPaidAmount() {
        java.math.BigDecimal amount = paymentOrderMapper.sumPaidAmount();
        return amount == null ? java.math.BigDecimal.ZERO : amount;
    }
}
