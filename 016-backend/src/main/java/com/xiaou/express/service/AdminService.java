package com.xiaou.express.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.express.common.BusinessException;
import com.xiaou.express.common.Constants;
import com.xiaou.express.dto.AdminComplaintHandleRequest;
import com.xiaou.express.dto.AdminOrderHandleRequest;
import com.xiaou.express.entity.Complaint;
import com.xiaou.express.entity.Orders;
import com.xiaou.express.entity.Transaction;
import com.xiaou.express.entity.User;
import com.xiaou.express.mapper.ComplaintMapper;
import com.xiaou.express.mapper.OrdersMapper;
import com.xiaou.express.mapper.TransactionMapper;
import com.xiaou.express.mapper.UserMapper;
import com.xiaou.express.vo.ComplaintVO;
import com.xiaou.express.vo.OrderVO;
import com.xiaou.express.vo.StatisticsVO;
import com.xiaou.express.vo.TransactionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AdminService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private ComplaintMapper complaintMapper;

    @Autowired
    private WalletService walletService;

    @Autowired
    private NotificationService notificationService;

    public StatisticsVO getStatistics() {
        StatisticsVO vo = new StatisticsVO();
        long totalUsers = userMapper.selectCount(null);
        long totalOrders = ordersMapper.selectCount(null);

        vo.setTotalUsers(totalUsers);
        vo.setTotalOrders(totalOrders);

        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime weekStart = LocalDateTime.now().minusDays(7);
        LocalDateTime monthStart = LocalDateTime.now().minusDays(30);

        vo.setTodayOrders(countOrdersSince(todayStart));
        vo.setWeekOrders(countOrdersSince(weekStart));
        vo.setMonthOrders(countOrdersSince(monthStart));
        vo.setCompletedOrders(countOrdersByStatus(Constants.OrderStatus.COMPLETED));
        vo.setCancelledOrders(countOrdersByStatus(Constants.OrderStatus.CANCELLED));
        vo.setPendingComplaints(countComplaintsByStatus(Constants.ComplaintStatus.PENDING));
        vo.setPendingOrders(countOrdersByStatus(Constants.OrderStatus.PENDING));
        vo.setProcessingOrders(countOrdersByStatuses(
                Constants.OrderStatus.ACCEPTED,
                Constants.OrderStatus.PICKED_UP,
                Constants.OrderStatus.DELIVERING
        ));
        vo.setActiveUsersToday(countActiveUsersSince(todayStart));
        vo.setActiveUsersWeek(countActiveUsersSince(weekStart));
        vo.setActiveUsersMonth(countActiveUsersSince(monthStart));
        vo.setTotalAmount(sumCompletedOrderAmount());

        if (totalOrders == 0) {
            vo.setCompletionRate(BigDecimal.ZERO);
        } else {
            vo.setCompletionRate(BigDecimal.valueOf(vo.getCompletedOrders() * 100.0 / totalOrders)
                    .setScale(2, RoundingMode.HALF_UP));
        }

        return vo;
    }

    public Page<User> getUsers(int pageNum, int pageSize, String keyword, Integer status) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            String trimmedKeyword = keyword.trim();
            wrapper.and(query -> query.like(User::getStudentId, trimmedKeyword)
                    .or()
                    .like(User::getUsername, trimmedKeyword)
                    .or()
                    .like(User::getPhone, trimmedKeyword)
                    .or()
                    .like(User::getRealName, trimmedKeyword));
        }
        if (status != null) {
            wrapper.eq(User::getStatus, status);
        }
        wrapper.orderByDesc(User::getCreateTime);
        return userMapper.selectPage(page, wrapper);
    }

    public Page<OrderVO> getOrders(int pageNum, int pageSize, String orderNo, Integer status) {
        Page<Orders> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(orderNo)) {
            wrapper.like(Orders::getOrderNo, orderNo.trim());
        }
        if (status != null) {
            wrapper.eq(Orders::getStatus, status);
        }
        wrapper.orderByDesc(Orders::getCreateTime);
        Page<Orders> orderPage = ordersMapper.selectPage(page, wrapper);

        Page<OrderVO> voPage = new Page<>();
        voPage.setCurrent(orderPage.getCurrent());
        voPage.setSize(orderPage.getSize());
        voPage.setTotal(orderPage.getTotal());
        voPage.setRecords(orderPage.getRecords().stream().map(this::convertToOrderVO).toList());
        return voPage;
    }

    public Page<ComplaintVO> getComplaints(int pageNum, int pageSize, Integer status) {
        Page<Complaint> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Complaint> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Complaint::getStatus, status);
        }
        wrapper.orderByDesc(Complaint::getCreateTime);
        Page<Complaint> complaintPage = complaintMapper.selectPage(page, wrapper);

        Page<ComplaintVO> voPage = new Page<>();
        voPage.setCurrent(complaintPage.getCurrent());
        voPage.setSize(complaintPage.getSize());
        voPage.setTotal(complaintPage.getTotal());
        voPage.setRecords(complaintPage.getRecords().stream().map(this::convertToComplaintVO).toList());
        return voPage;
    }

    public Page<TransactionVO> getTransactions(int pageNum, int pageSize, Integer type, Long userId) {
        Page<Transaction> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Transaction> wrapper = new LambdaQueryWrapper<>();
        if (type != null) {
            wrapper.eq(Transaction::getType, type);
        }
        if (userId != null) {
            wrapper.eq(Transaction::getUserId, userId);
        }
        wrapper.orderByDesc(Transaction::getCreateTime);
        Page<Transaction> transactionPage = transactionMapper.selectPage(page, wrapper);

        Page<TransactionVO> voPage = new Page<>();
        voPage.setCurrent(transactionPage.getCurrent());
        voPage.setSize(transactionPage.getSize());
        voPage.setTotal(transactionPage.getTotal());
        voPage.setRecords(transactionPage.getRecords().stream().map(this::convertToTransactionVO).toList());
        return voPage;
    }

    public void updateUserStatus(Long userId, Integer status) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setStatus(status);
        userMapper.updateById(user);
    }

    @Transactional
    public void handleOrder(Long adminId, Long orderId, AdminOrderHandleRequest request) {
        if (request == null || !StringUtils.hasText(request.getAction())) {
            throw new BusinessException("处理动作不能为空");
        }
        if (!"CANCEL".equalsIgnoreCase(request.getAction())) {
            throw new BusinessException("当前仅支持强制取消订单");
        }

        Orders order = ordersMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() == Constants.OrderStatus.COMPLETED
                || order.getStatus() == Constants.OrderStatus.CANCELLED
                || order.getStatus() == Constants.OrderStatus.REFUNDED) {
            throw new BusinessException("当前订单状态不允许强制取消");
        }

        walletService.refund(order.getPublisherId(), order.getFee(), "管理员取消订单退款", order.getId());

        order.setStatus(Constants.OrderStatus.CANCELLED);
        order.setCancelReason(StringUtils.hasText(request.getReason()) ? request.getReason().trim() : "管理员强制取消");
        order.setUpdateTime(LocalDateTime.now());
        ordersMapper.updateById(order);

        notificationService.sendNotification(order.getPublisherId(), Constants.NotificationType.ORDER,
                "订单已被管理员取消", "订单" + order.getOrderNo() + "已被管理员取消，跑腿费已退回", "order", order.getId());
        if (order.getTakerId() != null) {
            notificationService.sendNotification(order.getTakerId(), Constants.NotificationType.ORDER,
                    "订单已被管理员取消", "订单" + order.getOrderNo() + "已被管理员取消", "order", order.getId());
        }
    }

    @Transactional
    public void handleComplaint(Long adminId, Long complaintId, AdminComplaintHandleRequest request) {
        if (request == null || !StringUtils.hasText(request.getAction()) || !StringUtils.hasText(request.getResult())) {
            throw new BusinessException("处理动作和处理说明不能为空");
        }

        Complaint complaint = complaintMapper.selectById(complaintId);
        if (complaint == null) {
            throw new BusinessException("投诉不存在");
        }
        if (!Objects.equals(complaint.getStatus(), Constants.ComplaintStatus.PENDING)) {
            throw new BusinessException("该投诉已处理");
        }

        complaint.setHandleAdminId(adminId);
        complaint.setHandleTime(LocalDateTime.now());
        complaint.setHandleResult(request.getResult().trim());

        if ("ACCEPT".equalsIgnoreCase(request.getAction())) {
            complaint.setStatus(Constants.ComplaintStatus.HANDLED);
            deductRespondentCredit(complaint.getRespondentId());
            refundOrderIfNeeded(complaint.getOrderId(), request.getResult().trim());
        } else if ("REJECT".equalsIgnoreCase(request.getAction())) {
            complaint.setStatus(Constants.ComplaintStatus.REJECTED);
        } else {
            throw new BusinessException("不支持的处理动作");
        }

        complaintMapper.updateById(complaint);

        notificationService.sendNotification(complaint.getComplainantId(), Constants.NotificationType.SYSTEM,
                "投诉已处理", "您提交的投诉已处理：" + complaint.getHandleResult(), "complaint", complaint.getId());
        notificationService.sendNotification(complaint.getRespondentId(), Constants.NotificationType.SYSTEM,
                "投诉处理结果通知", "关于订单投诉的处理结果：" + complaint.getHandleResult(), "complaint", complaint.getId());
    }

    private void deductRespondentCredit(Long respondentId) {
        User respondent = userMapper.selectById(respondentId);
        if (respondent == null) {
            return;
        }
        int currentScore = respondent.getCreditScore() == null ? 100 : respondent.getCreditScore();
        respondent.setCreditScore(Math.max(0, currentScore - 20));
        userMapper.updateById(respondent);
    }

    private void refundOrderIfNeeded(Long orderId, String result) {
        Orders order = ordersMapper.selectById(orderId);
        if (order == null) {
            return;
        }
        if (order.getStatus() == Constants.OrderStatus.CANCELLED
                || order.getStatus() == Constants.OrderStatus.REFUNDED
                || order.getStatus() == Constants.OrderStatus.COMPLETED) {
            return;
        }

        walletService.refund(order.getPublisherId(), order.getFee(), "投诉成立退款", order.getId());
        order.setStatus(Constants.OrderStatus.REFUNDED);
        order.setCancelReason("投诉成立：" + result);
        order.setUpdateTime(LocalDateTime.now());
        ordersMapper.updateById(order);
    }

    private long countOrdersSince(LocalDateTime since) {
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(Orders::getCreateTime, since);
        return ordersMapper.selectCount(wrapper);
    }

    private long countOrdersByStatus(Integer status) {
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getStatus, status);
        return ordersMapper.selectCount(wrapper);
    }

    private long countOrdersByStatuses(Integer... statuses) {
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Orders::getStatus, (Object[]) statuses);
        return ordersMapper.selectCount(wrapper);
    }

    private long countComplaintsByStatus(Integer status) {
        LambdaQueryWrapper<Complaint> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Complaint::getStatus, status);
        return complaintMapper.selectCount(wrapper);
    }

    private long countActiveUsersSince(LocalDateTime since) {
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(Orders::getUpdateTime, since);
        Set<Long> activeUsers = ordersMapper.selectList(wrapper).stream()
                .flatMap(order -> Stream.of(order.getPublisherId(), order.getTakerId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return activeUsers.size();
    }

    private BigDecimal sumCompletedOrderAmount() {
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getStatus, Constants.OrderStatus.COMPLETED);
        return ordersMapper.selectList(wrapper).stream()
                .map(Orders::getFee)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderVO convertToOrderVO(Orders order) {
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

    private ComplaintVO convertToComplaintVO(Complaint complaint) {
        ComplaintVO vo = new ComplaintVO();
        BeanUtils.copyProperties(complaint, vo);

        Orders order = ordersMapper.selectById(complaint.getOrderId());
        if (order != null) {
            vo.setOrderNo(order.getOrderNo());
        }

        User complainant = userMapper.selectById(complaint.getComplainantId());
        if (complainant != null) {
            vo.setComplainantName(complainant.getRealName());
        }

        User respondent = userMapper.selectById(complaint.getRespondentId());
        if (respondent != null) {
            vo.setRespondentName(respondent.getRealName());
        }

        return vo;
    }

    private TransactionVO convertToTransactionVO(Transaction transaction) {
        TransactionVO vo = new TransactionVO();
        BeanUtils.copyProperties(transaction, vo);

        User user = userMapper.selectById(transaction.getUserId());
        if (user != null) {
            vo.setUserName(user.getRealName());
        }

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
