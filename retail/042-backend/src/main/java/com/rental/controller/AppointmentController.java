package com.rental.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rental.common.Result;
import com.rental.service.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 预约看房控制器
 */
@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    /**
     * 提交预约
     */
    @PostMapping
    public Result<?> create(HttpServletRequest request, @RequestBody AppointmentDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        appointmentService.create(userId, dto.getHouseId(), dto.getAppointmentTime(), 
                dto.getContactPhone(), dto.getRemark());
        return Result.success("预约成功");
    }

    /**
     * 获取预约列表
     */
    @GetMapping("/list")
    public Result<IPage<Map<String, Object>>> getList(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer status) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        IPage<Map<String, Object>> result = appointmentService.getList(userId, role, page, size, status);
        return Result.success(result);
    }

    /**
     * 确认预约（房东）
     */
    @PutMapping("/{id}/confirm")
    public Result<?> confirm(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        appointmentService.confirm(userId, id);
        return Result.success("确认成功");
    }

    /**
     * 拒绝预约（房东）
     */
    @PutMapping("/{id}/reject")
    public Result<?> reject(HttpServletRequest request, @PathVariable Long id, 
            @RequestBody RejectDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        appointmentService.reject(userId, id, dto.getReason());
        return Result.success("拒绝成功");
    }

    /**
     * 完成预约（房东）
     */
    @PutMapping("/{id}/complete")
    public Result<?> complete(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        appointmentService.complete(userId, id);
        return Result.success("操作成功");
    }

    /**
     * 取消预约（租客）
     */
    @PutMapping("/{id}/cancel")
    public Result<?> cancel(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        appointmentService.cancel(userId, id);
        return Result.success("取消成功");
    }

    @Data
    public static class AppointmentDTO {
        private Long houseId;
        private LocalDateTime appointmentTime;
        private String contactPhone;
        private String remark;
    }

    @Data
    public static class RejectDTO {
        private String reason;
    }
}
