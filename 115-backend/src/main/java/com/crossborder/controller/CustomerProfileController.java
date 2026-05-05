package com.crossborder.controller;

import com.crossborder.common.Result;
import com.crossborder.entity.CustomerProfile;
import com.crossborder.service.CustomerProfileService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerProfileController {
    private final CustomerProfileService service;

    @GetMapping("/page")
    public Result<IPage<CustomerProfile>> page(@RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody CustomerProfile entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody CustomerProfile entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/verify/{id}")
    public Result<Void> verify(@PathVariable Long id) {
        service.updateStatus(id, "VERIFIED");
        return Result.success();
    }

    @PutMapping("/freeze/{id}")
    public Result<Void> freeze(@PathVariable Long id) {
        service.updateStatus(id, "FROZEN");
        return Result.success();
    }

}
