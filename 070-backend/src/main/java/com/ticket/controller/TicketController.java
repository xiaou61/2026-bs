package com.ticket.controller;

import com.ticket.common.Result;
import com.ticket.service.TicketService;
import com.ticket.utils.AuthUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Resource
    private TicketService ticketService;

    @GetMapping("/my")
    public Result<?> my(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) String status,
                        HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(ticketService.page(pageNum, pageSize, status, userId, "USER"));
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String status,
                          HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(ticketService.page(pageNum, pageSize, status, userId, role));
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(ticketService.getById(id, userId, role));
    }

    @PostMapping("/verify")
    public Result<?> verify(@RequestBody Map<String, String> params, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        ticketService.verify(params.get("ticketNo"));
        return Result.success();
    }
}
