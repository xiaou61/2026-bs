package com.worksite.controller;

import com.github.pagehelper.PageInfo;
import com.worksite.common.Result;
import com.worksite.entity.AcceptanceRecord;
import com.worksite.service.AcceptanceRecordService;
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
@RequestMapping("/api/acceptance")
@RequiredArgsConstructor
public class AcceptanceRecordController {
    private final AcceptanceRecordService service;

    @GetMapping("/page")
    public Result<PageInfo<AcceptanceRecord>> page(@RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody AcceptanceRecord entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody AcceptanceRecord entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/pass/{id}")
    public Result<Void> pass(@PathVariable Long id) {
        service.updateStatus(id, "PASS");
        return Result.success();
    }

    @PutMapping("/fail/{id}")
    public Result<Void> fail(@PathVariable Long id) {
        service.updateStatus(id, "FAIL");
        return Result.success();
    }

}
