package com.xiaou.hair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.hair.common.Result;
import com.xiaou.hair.dto.CreateAppointmentDTO;
import com.xiaou.hair.entity.Appointment;
import com.xiaou.hair.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * 预约控制器
 */
@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    /**
     * 创建预约
     */
    @PostMapping("/create")
    public Result<Void> createAppointment(@Valid @RequestBody CreateAppointmentDTO dto) {
        appointmentService.createAppointment(dto);
        return Result.success("预约成功，请等待门店确认");
    }

    /**
     * 我的预约列表
     */
    @GetMapping("/my-list")
    public Result<Page<Appointment>> getMyAppointments(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String status) {
        Page<Appointment> page = appointmentService.getMyAppointments(pageNum, pageSize, status);
        return Result.success(page);
    }

    /**
     * 预约详情
     */
    @GetMapping("/{id}")
    public Result<Appointment> getAppointmentDetail(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return Result.success(appointment);
    }

    /**
     * 取消预约
     */
    @PutMapping("/{id}/cancel")
    public Result<Void> cancelAppointment(@PathVariable Long id, @RequestBody(required = false) Map<String, String> params) {
        String reason = params != null ? params.get("reason") : null;
        appointmentService.cancelAppointment(id, reason);
        return Result.success("预约已取消");
    }

    /**
     * 检查时段是否可预约
     */
    @GetMapping("/check-available")
    public Result<Boolean> checkTimeSlotAvailable(
            @RequestParam Long hairdresserId,
            @RequestParam String date,
            @RequestParam String time) {
        LocalDate appointmentDate = LocalDate.parse(date);
        LocalTime appointmentTime = LocalTime.parse(time);
        boolean available = appointmentService.isTimeSlotAvailable(hairdresserId, appointmentDate, appointmentTime);
        return Result.success(available);
    }

    /**
     * 获取可预约时段
     */
    @GetMapping("/available-times")
    public Result<List<LocalTime>> getAvailableTimeSlots(
            @RequestParam Long hairdresserId,
            @RequestParam String date) {
        LocalDate appointmentDate = LocalDate.parse(date);
        List<LocalTime> timeSlots = appointmentService.getAvailableTimeSlots(hairdresserId, appointmentDate);
        return Result.success(timeSlots);
    }
}
