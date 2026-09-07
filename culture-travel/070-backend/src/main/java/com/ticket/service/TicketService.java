package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.common.PageResult;
import com.ticket.entity.Ticket;
import com.ticket.entity.TicketOrder;
import com.ticket.mapper.TicketMapper;
import com.ticket.mapper.TicketOrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Resource
    private TicketMapper ticketMapper;

    @Resource
    private TicketOrderMapper ticketOrderMapper;

    public PageResult<Ticket> page(Integer pageNum, Integer pageSize, String status, Long userId, String role) {
        Page<Ticket> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Ticket> wrapper = new LambdaQueryWrapper<Ticket>()
                .eq(status != null && !status.trim().isEmpty(), Ticket::getStatus, status == null ? null : status.trim())
                .orderByDesc(Ticket::getId);
        if ("USER".equals(role)) {
            List<TicketOrder> orders = ticketOrderMapper.selectList(new LambdaQueryWrapper<TicketOrder>().eq(TicketOrder::getUserId, userId));
            Set<Long> orderIds = orders.stream().map(TicketOrder::getId).collect(Collectors.toSet());
            if (orderIds.isEmpty()) {
                PageResult<Ticket> result = new PageResult<>();
                result.setTotal(0L);
                result.setRecords(java.util.Collections.emptyList());
                return result;
            }
            wrapper.in(Ticket::getOrderId, orderIds);
        }
        Page<Ticket> resultPage = ticketMapper.selectPage(page, wrapper);
        PageResult<Ticket> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public Ticket getById(Long id, Long userId, String role) {
        Ticket ticket = ticketMapper.selectById(id);
        if (ticket == null) {
            throw new BusinessException("票券不存在");
        }
        if ("USER".equals(role)) {
            TicketOrder order = ticketOrderMapper.selectById(ticket.getOrderId());
            if (order == null || !order.getUserId().equals(userId)) {
                throw new BusinessException(403, "无权限");
            }
        }
        return ticket;
    }

    public void verify(String ticketNo) {
        Ticket ticket = ticketMapper.selectOne(new LambdaQueryWrapper<Ticket>()
                .eq(Ticket::getTicketNo, ticketNo)
                .last("limit 1"));
        if (ticket == null) {
            throw new BusinessException("票券不存在");
        }
        if (!"UNUSED".equals(ticket.getStatus())) {
            throw new BusinessException("票券不可核销");
        }
        ticket.setStatus("USED");
        ticket.setUseTime(LocalDateTime.now());
        ticketMapper.updateById(ticket);
    }
}
