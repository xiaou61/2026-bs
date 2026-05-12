package com.devopsrelease.controller;

import com.devopsrelease.common.Result;
import com.devopsrelease.entity.RollbackRecord;
import com.devopsrelease.service.AuthService;
import com.devopsrelease.service.RollbackRecordService;
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
@RequestMapping("/api/rollback-record")
@RequiredArgsConstructor
public class RollbackRecordController {
    private final RollbackRecordService service;
    private final AuthService authService;

    @GetMapping("/page")
    public Result<PageInfo<RollbackRecord>> page(@RequestAttribute String role,
                                        @RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }


    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody RollbackRecord entity) {
        authService.assertAdminOrReleaseOrOps(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody RollbackRecord entity) {
        authService.assertAdminOrReleaseOrOps(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrRelease(role);
        service.delete(id);
        return Result.success();
    }



    @PutMapping("/start/{id}")
    public Result<Void> start(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOps(role);
        service.updateStatus(id, "RUNNING");
        return Result.success();
    }


    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOps(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }


    @PutMapping("/fail/{id}")
    public Result<Void> fail(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrOps(role);
        service.updateStatus(id, "FAILED");
        return Result.success();
    }

}
