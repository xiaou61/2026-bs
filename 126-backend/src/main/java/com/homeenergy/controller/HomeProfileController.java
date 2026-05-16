package com.homeenergy.controller;

import com.github.pagehelper.PageInfo;
import com.homeenergy.common.Result;
import com.homeenergy.entity.HomeProfile;
import com.homeenergy.service.AuthService;
import com.homeenergy.service.HomeProfileService;
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
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeProfileController {
    private final AuthService authService;
    private final HomeProfileService service;

    @GetMapping("/page")
    public Result<PageInfo<HomeProfile>> page(@RequestAttribute String role,
                                              @RequestParam(required = false) Integer pageNum,
                                              @RequestParam(required = false) Integer pageSize,
                                              @RequestParam(required = false) String keyword,
                                              @RequestParam(required = false) String status) {
        authService.assertAdminOrResidentOrAnalystOrMaintainer(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody HomeProfile entity) {
        authService.assertAdminOrResident(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody HomeProfile entity) {
        authService.assertAdminOrResident(role);
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
        authService.assertAdminOrResident(role);
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/disable/{id}")
    public Result<Void> disable(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrResident(role);
        service.updateStatus(id, "DISABLED");
        return Result.success();
    }
}
