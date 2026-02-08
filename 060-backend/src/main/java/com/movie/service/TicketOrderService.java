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
        Showtime showtime = showtimeMapper.selectById(order.getShowtimeId());
        if (showtime == null) {
            throw new BusinessException("场次不存在");
        }
        if (showtime.getAvailableSeats() < order.getSeatCount()) {
            throw new BusinessException("余座不足");
        }
        order.setOrderNo("ORD" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + RandomUtil.randomNumbers(4));
        order.setTotalPrice(showtime.getPrice().multiply(new BigDecimal(order.getSeatCount())));
        order.setStatus(0);
        ticketOrderMapper.insert(order);
        showtimeMapper.updateAvailableSeats(showtime.getId(), order.getSeatCount());
    }

    public PageInfo<TicketOrder> getPage(Integer pageNum, Integer pageSize, String orderNo, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(ticketOrderMapper.selectPage(orderNo, status));
    }

    public List<TicketOrder> getMyOrders(Long userId) {
        return ticketOrderMapper.selectByUserId(userId);
    }

    public void pay(Long id) {
        TicketOrder order = ticketOrderMapper.selectById(id);
        if (order == null || order.getStatus() != 0) {
            throw new BusinessException("订单状态异常");
        }
        ticketOrderMapper.updatePayTime(id);
    }

    public void cancel(Long id) {
        TicketOrder order = ticketOrderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        ticketOrderMapper.updateStatus(id, 3);
    }

    public void complete(Long id) {
        ticketOrderMapper.updateStatus(id, 2);
    }
}
