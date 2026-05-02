package com.eldercare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.common.Result;
import com.eldercare.entity.CheckAppointment;
import com.eldercare.service.AppointmentService;
import com.eldercare.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/list")
    public Result<Page<CheckAppointment>> list(@RequestParam(defaultValue = "1") int pageNum,
                                               @RequestParam(defaultValue = "10") int pageSize,
                                               @RequestParam(required = false) String appointmentNo,
                                               @RequestParam(required = false) Long elderId,
                                               @RequestParam(required = false) Integer status,
                                               HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "doctor", "nurse", "reception");
        return Result.success(appointmentService.page(pageNum, pageSize, appointmentNo, elderId, status));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody CheckAppointment checkAppointment,
                              @RequestAttribute("userId") String userId,
                              HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "reception");
        appointmentService.add(checkAppointment, Long.valueOf(userId));
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody CheckAppointment checkAppointment, HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "reception");
        appointmentService.update(checkAppointment);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<String> status(@RequestParam Long id, @RequestParam Integer status, HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "nurse", "reception");
        appointmentService.updateStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "reception");
        appointmentService.delete(id);
        return Result.success();
    }
}
