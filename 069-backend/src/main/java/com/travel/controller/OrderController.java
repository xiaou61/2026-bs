package com.teacher.controller;

import com.teacher.common.BusinessException;
import com.teacher.common.Result;
import com.teacher.entity.TravelOrder;
import com.teacher.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String orderNo,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) Long userId,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        return Result.success(orderService.page(pageNum, pageSize, orderNo, status, userId));
    }

    @GetMapping("/my-page")
    public Result<?> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(required = false) String orderNo,
                            @RequestParam(required = false) String status,
                            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(orderService.myPage(userId, pageNum, pageSize, orderNo, status));
    }

    @GetMapping("/export")
    public Result<?> export(@RequestParam(required = false) String orderNo,
                            @RequestParam(required = false) String status,
                            @RequestParam(required = false) Long userId,
                            HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        Long currentUserId = (Long) request.getAttribute("userId");
        Long targetUserId = "ADMIN".equals(role) ? userId : currentUserId;
        return Result.success(orderService.exportCsv(orderNo, status, targetUserId));
    }

    @PostMapping
    public Result<?> add(@RequestBody TravelOrder order, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(orderService.create(userId, order));
    }

    @PutMapping("/cancel")
    public Result<?> cancel(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        if (params.get("id") == null) {
            throw new BusinessException("id不能为空");
        }
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        orderService.cancel(userId, role, ((Number) params.get("id")).longValue());
        return Result.success();
    }

    @PutMapping("/pay")
    public Result<?> pay(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        if (params.get("id") == null) {
            throw new BusinessException("id不能为空");
        }
        Long userId = (Long) request.getAttribute("userId");
        orderService.pay(userId, ((Number) params.get("id")).longValue());
        return Result.success();
    }

    @PutMapping("/finish")
    public Result<?> finish(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        if (params.get("id") == null) {
            throw new BusinessException("id不能为空");
        }
        Long userId = (Long) request.getAttribute("userId");
        orderService.finish(userId, ((Number) params.get("id")).longValue());
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}

