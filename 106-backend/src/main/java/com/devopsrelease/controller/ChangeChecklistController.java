package com.devopsrelease.controller;

import com.devopsrelease.common.Result;
import com.devopsrelease.entity.ChangeChecklist;
import com.devopsrelease.service.ChangeChecklistService;
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
@RequestMapping("/api/checklist")
@RequiredArgsConstructor
public class ChangeChecklistController {
    private final ChangeChecklistService service;

    @GetMapping("/page")
    public Result<PageInfo<ChangeChecklist>> page(@RequestParam(required = false) Integer pageNum,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }


    @PostMapping
    public Result<Void> add(@RequestBody ChangeChecklist entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody ChangeChecklist entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }


}
