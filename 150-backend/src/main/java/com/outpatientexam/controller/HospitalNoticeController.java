package com.outpatientexam.controller;

import com.github.pagehelper.PageInfo;
import com.outpatientexam.common.Result;
import com.outpatientexam.entity.HospitalNotice;
import com.outpatientexam.service.AuthService;
import com.outpatientexam.service.HospitalNoticeService;
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
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class HospitalNoticeController {
    private final AuthService authService;
    private final HospitalNoticeService service;

    @GetMapping("/page")
    public Result<PageInfo<HospitalNotice>> page(@RequestAttribute String role,
                                                 @RequestParam(required = false) Integer pageNum,
                                                 @RequestParam(required = false) Integer pageSize,
                                                 @RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody HospitalNotice entity) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR", "TECHNICIAN");
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody HospitalNotice entity) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR", "TECHNICIAN");
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR", "TECHNICIAN");
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/publish/{id}")
    public Result<Void> publish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR", "TECHNICIAN");
        service.updateStatus(id, "PUBLISHED");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR", "TECHNICIAN");
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
