package com.p193.controller;

import com.github.pagehelper.PageInfo;
import com.p193.common.Result;
import com.p193.entity.BizRecord05;
import com.p193.service.AuthService;
import com.p193.service.BizRecord05Service;
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
@RequestMapping("/api/record05")
@RequiredArgsConstructor
public class BizRecord05Controller {
    private final AuthService authService;
    private final BizRecord05Service service;

    @GetMapping("/page")
    public Result<PageInfo<BizRecord05>> page(@RequestAttribute("role") String role, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String keyword, @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute("role") String role, @RequestBody BizRecord05 entity) {
        authService.assertAnyRole(role, "ADMIN", "MANAGER", "AUDITOR", "OPERATOR", "STAFF");
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute("role") String role, @RequestBody BizRecord05 entity) {
        authService.assertAnyRole(role, "ADMIN", "MANAGER", "AUDITOR", "OPERATOR", "STAFF");
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
        authService.assertAnyRole(role, "ADMIN", "MANAGER", "AUDITOR", "OPERATOR", "STAFF");
        service.updateStatus(id, "PROCESSING");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "MANAGER", "AUDITOR", "OPERATOR", "STAFF");
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
