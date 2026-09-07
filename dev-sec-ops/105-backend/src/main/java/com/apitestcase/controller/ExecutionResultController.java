package com.apitestcase.controller;

import com.apitestcase.common.Result;
import com.apitestcase.entity.ExecutionResult;
import com.apitestcase.service.AuthService;
import com.apitestcase.service.ExecutionResultService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
@RequestMapping("/api/execution-result")
@RequiredArgsConstructor
public class ExecutionResultController {
    private final ExecutionResultService service;
    private final AuthService authService;

    @GetMapping("/page")
    public Result<IPage<ExecutionResult>> page(@RequestAttribute String role,
                                     @RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }


    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody ExecutionResult entity) {
        authService.assertAdminOrTesterOrDeveloper(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody ExecutionResult entity) {
        authService.assertAdminOrTesterOrDeveloper(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrTesterOrDeveloper(role);
        service.delete(id);
        return Result.success();
    }



    @PutMapping("/confirm/{id}")
    public Result<Void> confirm(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrTesterOrDeveloper(role);
        service.updateStatus(id, "CONFIRMED");
        return Result.success();
    }


    @PutMapping("/reject/{id}")
    public Result<Void> reject(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrTesterOrDeveloper(role);
        service.updateStatus(id, "REJECTED");
        return Result.success();
    }

}
