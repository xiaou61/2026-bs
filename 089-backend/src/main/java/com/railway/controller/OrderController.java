package com.railway.controller;

import com.railway.common.Result;
import com.railway.dto.OrderCreateDTO;
import com.railway.service.OrderService;
import com.railway.utils.AuthUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/create")
    public Result<?> create(@RequestBody OrderCreateDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(orderService.create(userId, dto));
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String orderNo,
                          @RequestParam(required = false) String status,
                          HttpServletRequest request) {
        AuthUtils.requireStaff((String) request.getAttribute("role"));
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(orderService.page(pageNum, pageSize, orderNo, status, userId, role));
    }

    @GetMapping("/my")
    public Result<?> my(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) String orderNo,
                        @RequestParam(required = false) String status,
                        HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(orderService.page(pageNum, pageSize, orderNo, status, userId, "USER"));
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(orderService.getById(id, userId, role));
    }

    @PutMapping("/cancel/{id}")
    public Result<?> cancel(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        orderService.cancel(id, userId, role);
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<?> finish(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        orderService.finish(id, userId);
        return Result.success();
    }
}
