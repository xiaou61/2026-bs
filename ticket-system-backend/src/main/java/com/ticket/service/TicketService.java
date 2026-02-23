package com.ticket.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.entity.Order;
import com.ticket.entity.Ticket;
import com.ticket.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketService {

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private OrderService orderService;

    public void generateTickets(Long orderId) {
        Order order = orderService.getOrderById(orderId);
        String[] seats = order.getSeatInfo().split(",");

        for (String seat : seats) {
            Ticket ticket = new Ticket();
            ticket.setOrderId(orderId);
            ticket.setTicketNo("TKT" + IdUtil.getSnowflakeNextIdStr());
            ticket.setQrCode("QR_" + ticket.getTicketNo());
            ticket.setStatus("unused");
            ticketMapper.insert(ticket);
        }
    }

    public Page<Ticket> getMyTickets(Long userId, Integer pageNum, Integer pageSize, String status) {
        Page<Ticket> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Ticket> wrapper = new QueryWrapper<>();
        wrapper.inSql("order_id", "SELECT id FROM orders WHERE user_id = " + userId);
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return ticketMapper.selectPage(page, wrapper);
    }

    public Ticket getTicketById(Long id) {
        Ticket ticket = ticketMapper.selectById(id);
        if (ticket == null) {
            throw new BusinessException("票券不存在");
        }
        return ticket;
    }

    public void verifyTicket(String ticketNo) {
        Ticket ticket = ticketMapper.selectOne(new QueryWrapper<Ticket>().eq("ticket_no", ticketNo));
        if (ticket == null) {
            throw new BusinessException("票券不存在");
        }

        if ("used".equals(ticket.getStatus())) {
            throw new BusinessException("票券已使用");
        }

        if ("expired".equals(ticket.getStatus())) {
            throw new BusinessException("票券已过期");
        }

        ticket.setStatus("used");
        ticket.setUseTime(LocalDateTime.now());
        ticketMapper.updateById(ticket);
    }
}
