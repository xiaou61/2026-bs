package com.apitestcase.controller;

import com.apitestcase.common.Result;
import com.apitestcase.entity.RequestParam;
import com.apitestcase.service.RequestParamService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/request-param")
@RequiredArgsConstructor
public class RequestParamController {
    private final RequestParamService service;

    @GetMapping("/page")
    public Result<IPage<RequestParam>> page(@org.springframework.web.bind.annotation.RequestParam(required = false) Integer pageNum,
                                     @org.springframework.web.bind.annotation.RequestParam(required = false) Integer pageSize,
                                     @org.springframework.web.bind.annotation.RequestParam(required = false) String keyword,
                                     @org.springframework.web.bind.annotation.RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }


    @PostMapping
    public Result<Void> add(@RequestBody RequestParam entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody RequestParam entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }


}
