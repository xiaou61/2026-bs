package com.outpatientexam.controller;

import com.github.pagehelper.PageInfo;
import com.outpatientexam.common.Result;
import com.outpatientexam.entity.ExamAppointment;
import com.outpatientexam.service.AuthService;
import com.outpatientexam.service.ExamAppointmentService;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
public class ExamAppointmentController {
    private final AuthService authService;
    private final ExamAppointmentService service;

    @GetMapping("/page")
    public Result<PageInfo<ExamAppointment>> page(@RequestAttribute("role") String role,
                                                  @RequestParam(required = false) Integer pageNum,
                                                  @RequestParam(required = false) Integer pageSize,
                                                  @RequestParam(required = false) String keyword,
                                                  @RequestParam(required = false) String status) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR", "PATIENT");
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute("role") String role, @RequestBody ExamAppointment entity) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR", "PATIENT");
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute("role") String role, @RequestBody ExamAppointment entity) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR", "PATIENT");
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR", "PATIENT");
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/submit/{id}")
    public Result<Void> submit(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR", "PATIENT");
        service.updateStatus(id, "SUBMITTED");
        return Result.success();
    }

    @PutMapping("/approve/{id}")
    public Result<Void> approve(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR");
        service.updateStatus(id, "APPROVED");
        return Result.success();
    }
}
