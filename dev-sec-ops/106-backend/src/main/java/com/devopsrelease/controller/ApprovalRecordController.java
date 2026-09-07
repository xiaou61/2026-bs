package com.devopsrelease.controller;

import com.devopsrelease.common.Result;
import com.devopsrelease.entity.ApprovalRecord;
import com.devopsrelease.service.AuthService;
import com.devopsrelease.service.ApprovalRecordService;
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
@RequestMapping("/api/approval-record")
@RequiredArgsConstructor
public class ApprovalRecordController {
    private final ApprovalRecordService service;
    private final AuthService authService;

    @GetMapping("/page")
    public Result<PageInfo<ApprovalRecord>> page(@RequestAttribute String role,
                                        @RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        authService.assertAdminOrReleaseOrAuditor(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }


    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody ApprovalRecord entity) {
        authService.assertAdminOrReleaseOrAuditor(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody ApprovalRecord entity) {
        authService.assertAdminOrReleaseOrAuditor(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrReleaseOrAuditor(role);
        service.delete(id);
        return Result.success();
    }



    @PutMapping("/approve/{id}")
    public Result<Void> approve(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrReleaseOrAuditor(role);
        service.updateStatus(id, "APPROVED");
        return Result.success();
    }


    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrReleaseOrAuditor(role);
        service.updateStatus(id, "REJECTED");
        return Result.success();
    }

}
