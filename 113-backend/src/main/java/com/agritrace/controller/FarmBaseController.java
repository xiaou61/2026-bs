package com.agritrace.controller;

import com.agritrace.common.Result;
import com.agritrace.entity.FarmBase;
import com.agritrace.service.FarmBaseService;
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
@RequestMapping("/api/farm")
@RequiredArgsConstructor
public class FarmBaseController {
    private final FarmBaseService service;

    @GetMapping("/page")
    public Result<IPage<FarmBase>> page(@RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody FarmBase entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody FarmBase entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/certify/{id}")
    public Result<Void> certify(@PathVariable Long id) {
        service.updateStatus(id, "CERTIFIED");
        return Result.success();
    }

    @PutMapping("/suspend/{id}")
    public Result<Void> suspend(@PathVariable Long id) {
        service.updateStatus(id, "SUSPENDED");
        return Result.success();
    }

}
