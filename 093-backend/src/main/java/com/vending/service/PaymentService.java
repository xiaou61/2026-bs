package com.vending.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vending.common.BusinessException;
import com.vending.common.PageResult;
import com.vending.dto.PaymentDTO;
import com.vending.dto.RechargeDTO;
import com.vending.entity.PaymentRecord;
import com.vending.entity.SaleOrder;
import com.vending.entity.User;
import com.vending.mapper.PaymentRecordMapper;
import com.vending.mapper.SaleOrderMapper;
import com.vending.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Resource
    private PaymentRecordMapper paymentMapper;

    @Resource
    private UserService userService;

    @Resource
    private OrderService orderService;

    @Resource
    private ShipmentService shipmentService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private SaleOrderMapper orderMapper;

    @Transactional(rollbackFor = Exception.class)
    public void recharge(Long userId, RechargeDTO dto) {
        if (dto == null || dto.getAmount() == null || dto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("充值金额不正确");
        }
        userService.changeBalance(userId, dto.getAmount());
        PaymentRecord record = new PaymentRecord();
        record.setUserId(userId);
        record.setPayNo(buildPayNo());
        record.setPayType("RECHARGE");
        record.setPayAmount(dto.getAmount());
        record.setStatus("SUCCESS");
        record.setPayTime(LocalDateTime.now());
        paymentMapper.insert(record);
    }

    @Transactional(rollbackFor = Exception.class)
    public void balancePay(Long userId, PaymentDTO dto) {
        if (dto == null || dto.getOrderId() == null) {
            throw new BusinessException("订单参数不能为空");
        }
        SaleOrder order = orderService.getPayableOrder(userId, dto.getOrderId());
        userService.changeBalance(userId, order.getPayAmount().negate());
        PaymentRecord record = new PaymentRecord();
        record.setUserId(userId);
        record.setOrderId(order.getId());
        record.setPayNo(buildPayNo());
        record.setPayType("BALANCE");
        record.setPayAmount(order.getPayAmount());
        record.setStatus("SUCCESS");
        record.setPayTime(LocalDateTime.now());
        paymentMapper.insert(record);
        orderService.markPaid(order.getId());
        shipmentService.createForOrder(order);
        orderService.markShipped(order.getId());
        userService.addConsumeAmount(userId, order.getPayAmount());
    }

    public PageResult<PaymentRecord> page(Integer pageNum, Integer pageSize, String payType, String status, Long userId, String role) {
        Page<PaymentRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<PaymentRecord> wrapper = new LambdaQueryWrapper<PaymentRecord>()
                .eq(payType != null && !payType.trim().isEmpty(), PaymentRecord::getPayType, payType == null ? null : payType.trim().toUpperCase())
                .eq(status != null && !status.trim().isEmpty(), PaymentRecord::getStatus, status == null ? null : status.trim().toUpperCase())
                .eq("CUSTOMER".equalsIgnoreCase(role), PaymentRecord::getUserId, userId)
                .orderByDesc(PaymentRecord::getId);
        Page<PaymentRecord> resultPage = paymentMapper.selectPage(page, wrapper);
        fillDetail(resultPage.getRecords());
        PageResult<PaymentRecord> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<PaymentRecord> listSuccessRecords() {
        List<PaymentRecord> list = paymentMapper.selectList(new LambdaQueryWrapper<PaymentRecord>()
                .eq(PaymentRecord::getStatus, "SUCCESS")
                .orderByDesc(PaymentRecord::getPayTime));
        fillDetail(list);
        return list;
    }

    private String buildPayNo() {
        return "PAY" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + RandomUtil.randomNumbers(4);
    }

    private void fillDetail(List<PaymentRecord> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, User> userMap = userMapper.selectList(new LambdaQueryWrapper<User>()
                        .in(User::getId, list.stream().map(PaymentRecord::getUserId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(User::getId, item -> item, (a, b) -> a, HashMap::new));
        Map<Long, String> orderMap = orderMapper.selectList(new LambdaQueryWrapper<SaleOrder>()
                        .in(SaleOrder::getId, list.stream().map(PaymentRecord::getOrderId).filter(java.util.Objects::nonNull).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(SaleOrder::getId, SaleOrder::getOrderNo, (a, b) -> a, HashMap::new));
        list.forEach(item -> {
            User user = userMap.get(item.getUserId());
            item.setUsername(user == null ? null : user.getUsername());
            item.setNickname(user == null ? null : user.getNickname());
            item.setOrderNo(item.getOrderId() == null ? null : orderMap.get(item.getOrderId()));
        });
    }
}
