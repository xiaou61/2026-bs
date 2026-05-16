package com.floodcity.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.floodcity.common.Result;
import com.floodcity.entity.RescueTeam;
import com.floodcity.service.AuthService;
import com.floodcity.service.RescueTeamService;
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
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class RescueTeamController {
    private final AuthService authService;
    private final RescueTeamService service;

    @GetMapping("/page")
    public Result<IPage<RescueTeam>> page(@RequestAttribute String role,
                                          @RequestParam(required = false) Integer pageNum,
                                          @RequestParam(required = false) Integer pageSize,
                                          @RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) String status) {
        authService.assertAdminOrDispatcherOrManager(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody RescueTeam entity) {
        authService.assertAdminOrDispatcherOrManager(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody RescueTeam entity) {
        authService.assertAdminOrDispatcherOrManager(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/enable/{id}")
    public Result<Void> enable(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDispatcherOrManager(role);
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/disable/{id}")
    public Result<Void> disable(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDispatcherOrManager(role);
        service.updateStatus(id, "DISABLED");
        return Result.success();
    }
}
