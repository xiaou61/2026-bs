package com.agriculture.controller;

import com.agriculture.common.Result;
import com.agriculture.entity.ExpertAppointment;
import com.agriculture.service.AppointmentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/page")
    public Result<Page<ExpertAppointment>> getPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                                    @RequestParam(required = false) Integer status) {
        return Result.success(appointmentService.getPage(pageNum, pageSize, status));
    }

    @GetMapping("/my")
    public Result<Page<ExpertAppointment>> getMy(@RequestParam Integer pageNum, @RequestParam Integer pageSize, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        return Result.success(appointmentService.getMyAppointments(userId, pageNum, pageSize));
    }

    @PostMapping
    public Result<?> add(@RequestBody ExpertAppointment appointment, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        appointment.setFarmerId(userId);
        appointmentService.save(appointment);
        return Result.success();
    }

    @PutMapping("/{id}/confirm")
    public Result<?> confirm(@PathVariable Long id) {
        appointmentService.confirm(id);
        return Result.success();
    }

    @PutMapping("/{id}/complete")
    public Result<?> complete(@PathVariable Long id) {
        appointmentService.complete(id);
        return Result.success();
    }

    @PutMapping("/{id}/cancel")
    public Result<?> cancel(@PathVariable Long id) {
        appointmentService.cancel(id);
        return Result.success();
    }

    @PostMapping("/{id}/rate")
    public Result<?> rate(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Integer rating = Integer.parseInt(body.get("rating").toString());
        String feedback = (String) body.get("feedback");
        appointmentService.rate(id, rating, feedback);
        return Result.success();
    }
}
