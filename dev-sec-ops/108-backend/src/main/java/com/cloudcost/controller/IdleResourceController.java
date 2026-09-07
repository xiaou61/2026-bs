package com.cloudcost.controller;

import com.cloudcost.common.Result;
import com.cloudcost.entity.IdleResource;
import com.cloudcost.service.AuthService;
import com.cloudcost.service.IdleResourceService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/api/idle-resource")
@RequiredArgsConstructor
public class IdleResourceController {
    private final AuthService authService;
    private final IdleResourceService service;

    @GetMapping("/page")
    public Result<PageInfo<IdleResource>> page(@RequestAttribute String role,
                                      @RequestParam(required = false) Integer pageNum,
                                      @RequestParam(required = false) Integer pageSize,
                                      @RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) String status) {
        authService.assertAdminOrDevopsOrManager(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody IdleResource entity) {
        authService.assertAdminOrDevops(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody IdleResource entity) {
        authService.assertAdminOrDevops(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/confirm/{id}")
    public Result<Void> confirm(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDevops(role);
        service.updateStatus(id, "CONFIRMED");
        return Result.success();
    }

    @PutMapping("/ignore/{id}")
    public Result<Void> ignore(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDevops(role);
        service.updateStatus(id, "IGNORED");
        return Result.success();
    }

    @PutMapping("/clean/{id}")
    public Result<Void> clean(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrDevops(role);
        service.updateStatus(id, "CLEANED");
        return Result.success();
    }
}
