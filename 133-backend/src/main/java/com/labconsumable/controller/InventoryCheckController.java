package com.labconsumable.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.labconsumable.common.Result;
import com.labconsumable.entity.InventoryCheck;
import com.labconsumable.service.AuthService;
import com.labconsumable.service.InventoryCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check")
@RequiredArgsConstructor
public class InventoryCheckController {
    private final AuthService authService;
    private final InventoryCheckService service;

    @GetMapping("/page")
    public Result<IPage<InventoryCheck>> page(@RequestAttribute String role,
                                              @RequestParam(required = false) Integer pageNum,
                                              @RequestParam(required = false) Integer pageSize,
                                              @RequestParam(required = false) String keyword,
                                              @RequestParam(required = false) String status) {
        authService.assertAdminOrKeeper(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody InventoryCheck entity) {
        authService.assertAdminOrKeeper(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody InventoryCheck entity) {
        authService.assertAdminOrKeeper(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/process/{id}")
    public Result<Void> process(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrKeeper(role);
        service.updateStatus(id, "PROCESSING");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrKeeper(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }

}
