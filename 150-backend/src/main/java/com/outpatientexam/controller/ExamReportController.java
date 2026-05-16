package com.outpatientexam.controller;

import com.github.pagehelper.PageInfo;
import com.outpatientexam.common.Result;
import com.outpatientexam.entity.ExamReport;
import com.outpatientexam.service.AuthService;
import com.outpatientexam.service.ExamReportService;
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
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ExamReportController {
    private final AuthService authService;
    private final ExamReportService service;

    @GetMapping("/page")
    public Result<PageInfo<ExamReport>> page(@RequestAttribute("role") String role,
                                             @RequestParam(required = false) Integer pageNum,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) String status) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR", "TECHNICIAN", "PATIENT");
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute("role") String role, @RequestBody ExamReport entity) {
        authService.assertAnyRole(role, "ADMIN", "TECHNICIAN");
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute("role") String role, @RequestBody ExamReport entity) {
        authService.assertAnyRole(role, "ADMIN", "TECHNICIAN");
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "TECHNICIAN");
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/process/{id}")
    public Result<Void> process(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "TECHNICIAN");
        service.updateStatus(id, "PROCESSING");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "TECHNICIAN");
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
