package com.railway.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.railway.common.BusinessException;
import com.railway.common.PageResult;
import com.railway.entity.Ticket;
import com.railway.entity.TicketOrder;
import com.railway.mapper.TicketMapper;
import com.railway.mapper.TicketOrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
        Page<Ticket> resultPage = ticketMapper.selectPage(page, wrapper);
        if ("USER".equalsIgnoreCase(role)) {
            resultPage.setRecords(resultPage.getRecords().stream()
                    .filter(ticket -> {
                        TicketOrder order = ticketOrderMapper.selectById(ticket.getOrderId());
                        return order != null && order.getUserId().equals(userId);
                    })
                    .collect(java.util.stream.Collectors.toList()));
            resultPage.setTotal(resultPage.getRecords().size());
        }
        PageResult<Ticket> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public Ticket getById(Long id, Long userId, String role) {
        Ticket ticket = ticketMapper.selectById(id);
        if (ticket == null) {
            throw new BusinessException("车票不存在");
        }
        if ("USER".equalsIgnoreCase(role)) {
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
            throw new BusinessException("电子票不存在");
        }
        if (!"UNUSED".equals(ticket.getStatus())) {
            throw new BusinessException("当前电子票不可核验");
        }
        ticket.setStatus("USED");
        ticket.setVerifyTime(LocalDateTime.now());
        ticketMapper.updateById(ticket);
    }
}
