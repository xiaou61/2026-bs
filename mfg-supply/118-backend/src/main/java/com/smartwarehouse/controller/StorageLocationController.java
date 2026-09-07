package com.smartwarehouse.controller;

import com.github.pagehelper.PageInfo;
import com.smartwarehouse.common.Result;
import com.smartwarehouse.entity.StorageLocation;
import com.smartwarehouse.service.AuthService;
import com.smartwarehouse.service.StorageLocationService;
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
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class StorageLocationController {
    private final AuthService authService;
    private final StorageLocationService service;

    @GetMapping("/page")
    public Result<PageInfo<StorageLocation>> page(@RequestAttribute String role,
                                                  @RequestParam(required = false) Integer pageNum,
                                                  @RequestParam(required = false) Integer pageSize,
                                                  @RequestParam(required = false) String keyword,
                                                  @RequestParam(required = false) String status) {
        authService.assertAdminOrKeeper(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody StorageLocation entity) {
        authService.assertAdminOrKeeper(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody StorageLocation entity) {
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

    @PutMapping("/reserve/{id}")
    public Result<Void> reserve(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrKeeper(role);
        service.updateStatus(id, "RESERVED");
        return Result.success();
    }

    @PutMapping("/release/{id}")
    public Result<Void> release(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrKeeper(role);
        service.updateStatus(id, "EMPTY");
        return Result.success();
    }

    @PutMapping("/lock/{id}")
    public Result<Void> lock(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrKeeper(role);
        service.updateStatus(id, "LOCKED");
        return Result.success();
    }
}
