package com.cloudcost.controller;

import com.cloudcost.common.Result;
import com.cloudcost.entity.BudgetPolicy;
import com.cloudcost.service.BudgetPolicyService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/api/budget")
@RequiredArgsConstructor
public class BudgetPolicyController {
    private final BudgetPolicyService service;

    @GetMapping("/page")
    public Result<PageInfo<BudgetPolicy>> page(@RequestParam(required = false) Integer pageNum,
                                      @RequestParam(required = false) Integer pageSize,
                                      @RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody BudgetPolicy entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody BudgetPolicy entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/enable/{id}")
    public Result<Void> enable(@PathVariable Long id) {
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/archive/{id}")
    public Result<Void> archive(@PathVariable Long id) {
        service.updateStatus(id, "ARCHIVED");
        return Result.success();
    }
}
