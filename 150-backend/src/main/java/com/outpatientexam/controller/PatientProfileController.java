package com.outpatientexam.controller;

import com.github.pagehelper.PageInfo;
import com.outpatientexam.common.Result;
import com.outpatientexam.entity.PatientProfile;
import com.outpatientexam.service.AuthService;
import com.outpatientexam.service.PatientProfileService;
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
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientProfileController {
    private final AuthService authService;
    private final PatientProfileService service;

    @GetMapping("/page")
    public Result<PageInfo<PatientProfile>> page(@RequestAttribute("role") String role,
                                                 @RequestParam(required = false) Integer pageNum,
                                                 @RequestParam(required = false) Integer pageSize,
                                                 @RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false) String status) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR", "PATIENT");
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute("role") String role, @RequestBody PatientProfile entity) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR");
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute("role") String role, @RequestBody PatientProfile entity) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR");
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR");
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/activate/{id}")
    public Result<Void> activate(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR");
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "DOCTOR");
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
