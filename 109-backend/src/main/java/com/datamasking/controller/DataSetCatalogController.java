package com.datamasking.controller;

import com.datamasking.common.Result;
import com.datamasking.entity.DataSetCatalog;
import com.datamasking.service.DataSetCatalogService;
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
@RequestMapping("/api/dataset")
@RequiredArgsConstructor
public class DataSetCatalogController {
    private final DataSetCatalogService service;

    @GetMapping("/page")
    public Result<IPage<DataSetCatalog>> page(@RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody DataSetCatalog entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody DataSetCatalog entity) {
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
        service.updateStatus(id, "ACTIVE");
        return Result.success();
    }

    @PutMapping("/archive/{id}")
    public Result<Void> archive(@PathVariable Long id) {
        service.updateStatus(id, "ARCHIVED");
        return Result.success();
    }

}
