package com.xiaou.express.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.express.common.Constants;
import com.xiaou.express.entity.Complaint;
import com.xiaou.express.entity.Orders;
import com.xiaou.express.entity.Transaction;
import com.xiaou.express.entity.User;
import com.xiaou.express.mapper.ComplaintMapper;
import com.xiaou.express.mapper.OrdersMapper;
import com.xiaou.express.mapper.TransactionMapper;
import com.xiaou.express.mapper.UserMapper;
import com.xiaou.express.vo.StatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    public StatisticsVO getStatistics() {
        StatisticsVO vo = new StatisticsVO();

        vo.setTotalUsers(userMapper.selectCount(null));
        vo.setTotalOrders(ordersMapper.selectCount(null));

        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LambdaQueryWrapper<Orders> todayWrapper = new LambdaQueryWrapper<>();
        todayWrapper.ge(Orders::getCreateTime, today);
        vo.setTodayOrders(ordersMapper.selectCount(todayWrapper));

        LocalDateTime weekStart = LocalDateTime.now().minusDays(7);
        LambdaQueryWrapper<Orders> weekWrapper = new LambdaQueryWrapper<>();
        weekWrapper.ge(Orders::getCreateTime, weekStart);
        vo.setWeekOrders(ordersMapper.selectCount(weekWrapper));

        LocalDateTime monthStart = LocalDateTime.now().minusDays(30);
        LambdaQueryWrapper<Orders> monthWrapper = new LambdaQueryWrapper<>();
        monthWrapper.ge(Orders::getCreateTime, monthStart);
        vo.setMonthOrders(ordersMapper.selectCount(monthWrapper));

        LambdaQueryWrapper<Orders> completedWrapper = new LambdaQueryWrapper<>();
        completedWrapper.eq(Orders::getStatus, Constants.OrderStatus.COMPLETED);
        vo.setCompletedOrders(ordersMapper.selectCount(completedWrapper));

        LambdaQueryWrapper<Orders> cancelledWrapper = new LambdaQueryWrapper<>();
        cancelledWrapper.eq(Orders::getStatus, Constants.OrderStatus.CANCELLED);
        vo.setCancelledOrders(ordersMapper.selectCount(cancelledWrapper));

        vo.setTotalAmount(BigDecimal.ZERO);

        return vo;
    }

    public Page<User> getUsers(int pageNum, int pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        return userMapper.selectPage(page, null);
    }

    public Page<Orders> getOrders(int pageNum, int pageSize) {
        Page<Orders> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Orders::getCreateTime);
        return ordersMapper.selectPage(page, wrapper);
    }

    public Page<Complaint> getComplaints(int pageNum, int pageSize) {
        Page<Complaint> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Complaint> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Complaint::getCreateTime);
        return complaintMapper.selectPage(page, wrapper);
    }

    public Page<Transaction> getTransactions(int pageNum, int pageSize) {
        Page<Transaction> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Transaction> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Transaction::getCreateTime);
        return transactionMapper.selectPage(page, wrapper);
    }

    public void updateUserStatus(Long userId, Integer status) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setStatus(status);
            userMapper.updateById(user);
        }
    }
}

