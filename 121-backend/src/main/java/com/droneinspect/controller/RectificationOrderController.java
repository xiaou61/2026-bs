package com.droneinspect.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.droneinspect.common.Result;
import com.droneinspect.entity.RectificationOrder;
import com.droneinspect.service.RectificationOrderService;
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
@RequestMapping("/api/rectify")
@RequiredArgsConstructor
public class RectificationOrderController {
    private final RectificationOrderService service;

    @GetMapping("/page")
    public Result<IPage<RectificationOrder>> page(@RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody RectificationOrder entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody RectificationOrder entity) {
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
        service.updateStatus(id, "ASSIGNED");
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@PathVariable Long id) {
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }

}
