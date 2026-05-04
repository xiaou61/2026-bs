package com.licensecheck.controller;

import com.licensecheck.common.Result;
import com.licensecheck.entity.RectificationTask;
import com.licensecheck.service.RectificationTaskService;
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
@RequestMapping("/api/rectification")
@RequiredArgsConstructor
public class RectificationTaskController {
    private final RectificationTaskService service;

    @GetMapping("/page")
    public Result<PageInfo<RectificationTask>> page(@RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }


    @PostMapping
    public Result<Void> add(@RequestBody RectificationTask entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody RectificationTask entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }



    @PutMapping("/assign/{id}")
    public Result<Void> assign(@PathVariable Long id) {
        service.updateStatus(id, "ASSIGNED");
        return Result.success();
    }


    @PutMapping("/finish/{id}")
    public Result<Void> finish(@PathVariable Long id) {
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }


    @PutMapping("/reopen/{id}")
    public Result<Void> reopen(@PathVariable Long id) {
        service.updateStatus(id, "REOPENED");
        return Result.success();
    }

}
