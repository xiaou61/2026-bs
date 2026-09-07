package com.clubfinance.controller;

import com.github.pagehelper.PageInfo;
import com.clubfinance.common.Result;
import com.clubfinance.entity.ReturnInspection;
import com.clubfinance.service.AuthService;
import com.clubfinance.service.ReturnInspectionService;
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
@RequestMapping("/api/returncheck")
@RequiredArgsConstructor
public class ReturnInspectionController {
    private final AuthService authService;
    private final ReturnInspectionService service;

    @GetMapping("/page")
    public Result<PageInfo<ReturnInspection>> page(@RequestAttribute("role") String role, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String keyword, @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute("role") String role, @RequestBody ReturnInspection entity) {
        authService.assertAnyRole(role, "ADMIN", "UNION", "WAREHOUSE", "CLUB");
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute("role") String role, @RequestBody ReturnInspection entity) {
        authService.assertAnyRole(role, "ADMIN", "UNION", "WAREHOUSE", "CLUB");
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/process/{id}")
    public Result<Void> process(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "UNION", "WAREHOUSE", "CLUB");
        service.updateStatus(id, "PROCESSING");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "UNION", "WAREHOUSE", "CLUB");
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
