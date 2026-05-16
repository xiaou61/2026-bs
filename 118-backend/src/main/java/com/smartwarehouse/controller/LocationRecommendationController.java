package com.smartwarehouse.controller;

import com.github.pagehelper.PageInfo;
import com.smartwarehouse.common.Result;
import com.smartwarehouse.entity.LocationRecommendation;
import com.smartwarehouse.service.AuthService;
import com.smartwarehouse.service.LocationRecommendationService;
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
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class LocationRecommendationController {
    private final AuthService authService;
    private final LocationRecommendationService service;

    @GetMapping("/page")
    public Result<PageInfo<LocationRecommendation>> page(@RequestAttribute String role,
                                                         @RequestParam(required = false) Integer pageNum,
                                                         @RequestParam(required = false) Integer pageSize,
                                                         @RequestParam(required = false) String keyword,
                                                         @RequestParam(required = false) String status) {
        authService.assertAdminOrDispatcherOrKeeper(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody LocationRecommendation entity) {
        authService.assertAdminOrKeeper(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody LocationRecommendation entity) {
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

    @PutMapping("/apply/{id}")
    public Result<Void> apply(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrKeeper(role);
        service.updateStatus(id, "APPLIED");
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrKeeper(role);
        service.updateStatus(id, "REJECTED");
        return Result.success();
    }
}
