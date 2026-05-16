package com.twinpark.controller;

import com.github.pagehelper.PageInfo;
import com.twinpark.common.Result;
import com.twinpark.entity.WorkOrder;
import com.twinpark.service.AuthService;
import com.twinpark.service.WorkOrderService;
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
@RequestMapping("/api/workOrder")
@RequiredArgsConstructor
public class WorkOrderController {
    private final AuthService authService;
    private final WorkOrderService service;

    @GetMapping("/page")
    public Result<PageInfo<WorkOrder>> page(@RequestAttribute String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        authService.assertAdminOrEngineerOrManager(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody WorkOrder entity) {
        authService.assertAdminOrEngineer(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody WorkOrder entity) {
        authService.assertAdminOrEngineer(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/accept/{id}")
    public Result<Void> accept(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrEngineer(role);
        service.updateStatus(id, "ACCEPTED");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrEngineer(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
