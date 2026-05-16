package com.agritrace.controller;

import com.agritrace.common.Result;
import com.agritrace.entity.RegulationCheck;
import com.agritrace.service.AuthService;
import com.agritrace.service.RegulationCheckService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
@RequestMapping("/api/regulation")
@RequiredArgsConstructor
public class RegulationCheckController {
    private final AuthService authService;
    private final RegulationCheckService service;

    @GetMapping("/page")
    public Result<IPage<RegulationCheck>> page(@RequestAttribute String role,
                                               @RequestParam(required = false) Integer pageNum,
                                               @RequestParam(required = false) Integer pageSize,
                                               @RequestParam(required = false) String keyword,
                                               @RequestParam(required = false) String status) {
        authService.assertAdminOrRegulator(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody RegulationCheck entity) {
        authService.assertAdminOrRegulator(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody RegulationCheck entity) {
        authService.assertAdminOrRegulator(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/publish/{id}")
    public Result<Void> publish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrRegulator(role);
        service.updateStatus(id, "PUBLISHED");
        return Result.success();
    }

    @PutMapping("/rectify/{id}")
    public Result<Void> rectify(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrRegulator(role);
        service.updateStatus(id, "RECTIFYING");
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrRegulator(role);
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }
}
