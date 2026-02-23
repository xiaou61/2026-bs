package com.ticket.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.Result;
import com.ticket.entity.Ticket;
import com.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/my")
    public Result<Page<Ticket>> getMyTickets(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String status,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(ticketService.getMyTickets(userId, pageNum, pageSize, status));
    }

    @GetMapping("/{id}")
    public Result<Ticket> getTicketById(@PathVariable Long id) {
        return Result.success(ticketService.getTicketById(id));
    }

    @PostMapping("/verify")
    public Result<Void> verifyTicket(@RequestParam String ticketNo) {
        ticketService.verifyTicket(ticketNo);
        return Result.success();
    }
}
