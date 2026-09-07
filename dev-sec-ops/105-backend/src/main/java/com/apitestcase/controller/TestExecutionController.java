package com.apitestcase.controller;

import com.apitestcase.common.Result;
import com.apitestcase.entity.TestExecution;
import com.apitestcase.service.AuthService;
import com.apitestcase.service.TestExecutionService;
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
@RequestMapping("/api/execution")
@RequiredArgsConstructor
public class TestExecutionController {
    private final TestExecutionService service;
    private final AuthService authService;

    @GetMapping("/page")
    public Result<IPage<TestExecution>> page(@RequestAttribute String role,
                                     @RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }


    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody TestExecution entity) {
        authService.assertAdminOrTesterOrDeveloper(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody TestExecution entity) {
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



    @PutMapping("/start/{id}")
    public Result<Void> start(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrTesterOrDeveloper(role);
        service.updateStatus(id, "RUNNING");
        return Result.success();
    }


    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrTesterOrDeveloper(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }


    @PutMapping("/fail/{id}")
    public Result<Void> fail(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrTesterOrDeveloper(role);
        service.updateStatus(id, "FAILED");
        return Result.success();
    }

}
