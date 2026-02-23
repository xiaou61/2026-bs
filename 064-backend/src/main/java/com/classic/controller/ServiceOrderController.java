package com.classic.controller;

import com.classic.common.BusinessException;
import com.classic.common.Result;
import com.classic.entity.ServiceOrder;
import com.classic.service.ServiceOrderService;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import java.util.Map;

@RestController
@RequestMapping("/api/service")
public class ServiceOrderController {

    @Resource
    private ServiceOrderService serviceOrderService;

    @GetMapping("/my-page")
    public Result<?> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(required = false) String orderNo,
                            @RequestParam(required = false) Integer status,
                            HttpServletRequest request) {
        return Result.success(serviceOrderService.myPage((Long) request.getAttribute("userId"), pageNum, pageSize, orderNo, status));
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String orderNo,
                          @RequestParam(required = false) Long userId,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        return Result.success(serviceOrderService.page(pageNum, pageSize, orderNo, userId, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody ServiceOrder order, HttpServletRequest request) {
        checkUser((String) request.getAttribute("role"));
        serviceOrderService.save(order, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        Long id = ((Number) params.get("id")).longValue();
        Integer status = ((Number) params.get("status")).intValue();
        String adminReply = params.get("adminReply") == null ? null : params.get("adminReply").toString();
        serviceOrderService.updateStatus(id, status, adminReply);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        serviceOrderService.deleteById(id, (Long) request.getAttribute("userId"), (String) request.getAttribute("role"));
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }

    private void checkUser(String role) {
        if (!"USER".equals(role)) {
            throw new BusinessException("仅用户可发起预约");
        }
    }
}
