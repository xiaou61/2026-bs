package com.agritrace.controller;

import com.agritrace.common.Result;
import com.agritrace.entity.RegulationCheck;
import com.agritrace.service.RegulationCheckService;
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
@RequestMapping("/api/regulation")
@RequiredArgsConstructor
public class RegulationCheckController {
    private final RegulationCheckService service;

    @GetMapping("/page")
    public Result<IPage<RegulationCheck>> page(@RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody RegulationCheck entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody RegulationCheck entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/publish/{id}")
    public Result<Void> publish(@PathVariable Long id) {
        service.updateStatus(id, "PUBLISHED");
        return Result.success();
    }

    @PutMapping("/rectify/{id}")
    public Result<Void> rectify(@PathVariable Long id) {
        service.updateStatus(id, "RECTIFYING");
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<Void> close(@PathVariable Long id) {
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }

}
