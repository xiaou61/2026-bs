package com.phishingtraining.controller;

import com.phishingtraining.common.Result;
import com.phishingtraining.entity.ClickTracking;
import com.phishingtraining.service.ClickTrackingService;
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
@RequestMapping("/api/click")
@RequiredArgsConstructor
public class ClickTrackingController {
    private final ClickTrackingService service;

    @GetMapping("/page")
    public Result<IPage<ClickTracking>> page(@RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody ClickTracking entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody ClickTracking entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/confirm/{id}")
    public Result<Void> confirm(@PathVariable Long id) {
        service.updateStatus(id, "CONFIRMED");
        return Result.success();
    }

    @PutMapping("/educate/{id}")
    public Result<Void> educate(@PathVariable Long id) {
        service.updateStatus(id, "TRAINED");
        return Result.success();
    }

    @PutMapping("/ignore/{id}")
    public Result<Void> ignore(@PathVariable Long id) {
        service.updateStatus(id, "IGNORED");
        return Result.success();
    }

}
