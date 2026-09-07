package com.licensecheck.controller;

import com.licensecheck.common.Result;
import com.licensecheck.entity.RepositoryBranch;
import com.licensecheck.service.AuthService;
import com.licensecheck.service.RepositoryBranchService;
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
@RequestMapping("/api/branch")
@RequiredArgsConstructor
public class RepositoryBranchController {
    private final RepositoryBranchService service;
    private final AuthService authService;

    @GetMapping("/page")
    public Result<PageInfo<RepositoryBranch>> page(@RequestAttribute String role,
                                        @RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }


    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody RepositoryBranch entity) {
        authService.assertAdminOrComplianceOrDeveloper(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody RepositoryBranch entity) {
        authService.assertAdminOrComplianceOrDeveloper(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrCompliance(role);
        service.delete(id);
        return Result.success();
    }


}
