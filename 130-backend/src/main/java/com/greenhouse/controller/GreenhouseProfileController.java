package com.greenhouse.controller;

import com.github.pagehelper.PageInfo;
import com.greenhouse.common.Result;
import com.greenhouse.entity.GreenhouseProfile;
import com.greenhouse.service.AuthService;
import com.greenhouse.service.GreenhouseProfileService;
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
@RequestMapping("/api/greenhouse")
@RequiredArgsConstructor
public class GreenhouseProfileController {
    private final AuthService authService;
    private final GreenhouseProfileService service;

    @GetMapping("/page")
    public Result<PageInfo<GreenhouseProfile>> page(@RequestAttribute String role,
                                                    @RequestParam(required = false) Integer pageNum,
                                                    @RequestParam(required = false) Integer pageSize,
                                                    @RequestParam(required = false) String keyword,
                                                    @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody GreenhouseProfile entity) {
        authService.assertAdminOrGrowerOrManager(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody GreenhouseProfile entity) {
        authService.assertAdminOrGrowerOrManager(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/activate/{id}")
    public Result<Void> activate(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrGrower(role);
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrManager(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }

}
