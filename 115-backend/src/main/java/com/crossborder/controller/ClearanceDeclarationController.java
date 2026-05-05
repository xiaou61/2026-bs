package com.crossborder.controller;

import com.crossborder.common.Result;
import com.crossborder.entity.ClearanceDeclaration;
import com.crossborder.service.ClearanceDeclarationService;
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
@RequestMapping("/api/declaration")
@RequiredArgsConstructor
public class ClearanceDeclarationController {
    private final ClearanceDeclarationService service;

    @GetMapping("/page")
    public Result<IPage<ClearanceDeclaration>> page(@RequestParam(required = false) Integer pageNum,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody ClearanceDeclaration entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody ClearanceDeclaration entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/submit/{id}")
    public Result<Void> submit(@PathVariable Long id) {
        service.updateStatus(id, "SUBMITTED");
        return Result.success();
    }

    @PutMapping("/release/{id}")
    public Result<Void> release(@PathVariable Long id) {
        service.updateStatus(id, "RELEASED");
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    public Result<Void> reject(@PathVariable Long id) {
        service.updateStatus(id, "REJECTED");
        return Result.success();
    }

}
