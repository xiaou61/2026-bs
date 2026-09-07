package com.xiaou.hair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.hair.common.Result;
import com.xiaou.hair.entity.ServiceEntity;
import com.xiaou.hair.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务项目控制器
 */
@RestController
@RequestMapping("/api/service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    /**
     * 服务项目列表
     */
    @GetMapping("/list")
    public Result<Page<ServiceEntity>> getServiceList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) String category) {
        Page<ServiceEntity> page = serviceService.getServiceList(pageNum, pageSize, storeId, category);
        return Result.success(page);
    }

    /**
     * 服务项目详情
     */
    @GetMapping("/{id}")
    public Result<ServiceEntity> getServiceDetail(@PathVariable Long id) {
        ServiceEntity service = serviceService.getServiceById(id);
        return Result.success(service);
    }

    /**
     * 服务分类列表
     */
    @GetMapping("/categories")
    public Result<List<String>> getServiceCategories() {
        List<String> categories = serviceService.getServiceCategories();
        return Result.success(categories);
    }
}
