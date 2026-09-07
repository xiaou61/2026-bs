package com.apitestcase.controller;

import com.apitestcase.common.Result;
import com.apitestcase.entity.RequestParam;
import com.apitestcase.service.AuthService;
import com.apitestcase.service.RequestParamService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/request-param")
@RequiredArgsConstructor
public class RequestParamController {
    private final RequestParamService service;
    private final AuthService authService;

    @GetMapping("/page")
    public Result<IPage<RequestParam>> page(@RequestAttribute String role,
                                     @org.springframework.web.bind.annotation.RequestParam(required = false) Integer pageNum,
                                     @org.springframework.web.bind.annotation.RequestParam(required = false) Integer pageSize,
                                     @org.springframework.web.bind.annotation.RequestParam(required = false) String keyword,
                                     @org.springframework.web.bind.annotation.RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }


    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody RequestParam entity) {
        authService.assertAdminOrProductOrDeveloper(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody RequestParam entity) {
        authService.assertAdminOrProductOrDeveloper(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrProduct(role);
        service.delete(id);
        return Result.success();
    }


}
