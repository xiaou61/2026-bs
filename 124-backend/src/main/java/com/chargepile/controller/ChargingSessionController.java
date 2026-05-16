package com.chargepile.controller;

import com.github.pagehelper.PageInfo;
import com.chargepile.common.Result;
import com.chargepile.entity.ChargingSession;
import com.chargepile.service.AuthService;
import com.chargepile.service.ChargingSessionService;
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
@RequestMapping("/api/session")
@RequiredArgsConstructor
public class ChargingSessionController {
    private final AuthService authService;
    private final ChargingSessionService service;

    @GetMapping("/page")
    public Result<PageInfo<ChargingSession>> page(@RequestAttribute String role,
                                                  @RequestParam(required = false) Integer pageNum,
                                                  @RequestParam(required = false) Integer pageSize,
                                                  @RequestParam(required = false) String keyword,
                                                  @RequestParam(required = false) String status) {
        authService.assertAdminOrOperatorOrOwner(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody ChargingSession entity) {
        authService.assertAdminOrOperatorOrOwner(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody ChargingSession entity) {
        authService.assertAdminOrOperator(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOperator(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOperator(role);
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }
}
