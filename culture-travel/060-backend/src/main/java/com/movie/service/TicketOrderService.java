package com.movie.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.common.BusinessException;
import com.movie.entity.Showtime;
import com.movie.entity.TicketOrder;
import com.movie.mapper.ShowtimeMapper;
import com.movie.mapper.TicketOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class TicketOrderService {

    @Resource
    private TicketOrderMapper ticketOrderMapper;

    @Resource
    private ShowtimeMapper showtimeMapper;

    @Transactional
    public void createOrder(TicketOrder order) {
        if (order.getShowtimeId() == null || order.getSeatCount() == null || order.getSeatCount() <= 0) {
            throw new BusinessException("订单信息不完整");
        }
        if (order.getSeats() == null || order.getSeats().trim().isEmpty()) {
            throw new BusinessException("请选择座位");
        }
        Showtime showtime = showtimeMapper.selectById(order.getShowtimeId());
        if (showtime == null) {
            throw new BusinessException("场次不存在");
        }
        if (showtime.getAvailableSeats() < order.getSeatCount()) {
            throw new BusinessException("余座不足");
        }
        order.setOrderNo("ORD" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + RandomUtil.randomNumbers(4));
        order.setTotalPrice(showtime.getPrice().multiply(BigDecimal.valueOf(order.getSeatCount())));
        order.setStatus(0);
        ticketOrderMapper.insert(order);
        if (showtimeMapper.updateAvailableSeats(showtime.getId(), order.getSeatCount()) == 0) {
            throw new BusinessException("余座不足");
        }
    }

    public PageInfo<TicketOrder> getPage(Integer pageNum, Integer pageSize, String orderNo, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(ticketOrderMapper.selectPage(orderNo, status));
    }

    public List<TicketOrder> getMyOrders(Long userId) {
        return ticketOrderMapper.selectByUserId(userId);
    }

    public void pay(Long id, Long userId, String role) {
        TicketOrder order = ticketOrderMapper.selectById(id);
        if (order == null || order.getStatus() != 0) {
            throw new BusinessException("订单状态异常");
        }
        checkOrderOwner(order, userId, role);
        ticketOrderMapper.updatePayTime(id);
    }

    public void cancel(Long id, Long userId, String role) {
        TicketOrder order = ticketOrderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        checkOrderOwner(order, userId, role);
        ticketOrderMapper.updateStatus(id, 3);
    }

    public void complete(Long id) {
        ticketOrderMapper.updateStatus(id, 2);
    }

    private void checkOrderOwner(TicketOrder order, Long userId, String role) {
        if ("admin".equals(role)) {
            return;
        }
        if (order.getUserId() == null || !order.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权限操作该订单");
        }
    }
}
