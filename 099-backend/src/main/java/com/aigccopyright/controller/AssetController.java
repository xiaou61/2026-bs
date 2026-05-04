package com.aigccopyright.controller;

import com.aigccopyright.common.Result;
import com.aigccopyright.entity.ImageAsset;
import com.aigccopyright.service.AuthService;
import com.aigccopyright.service.ImageAssetService;
import com.aigccopyright.service.OperationLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/asset")
public class AssetController {

    @Autowired
    private ImageAssetService imageAssetService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<ImageAsset>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                         String keyword,
                                         String category,
                                         Integer status) {
        return Result.success(imageAssetService.page(pageNum, pageSize, keyword, category, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ImageAsset entity) {
        authService.assertCreator(role);
        imageAssetService.saveEntity(entity, userId);
        operationLogService.record(userId, "图片作品", "新增", "新增作品：" + entity.getTitle());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody ImageAsset entity) {
        authService.assertCreator(role);
        imageAssetService.saveEntity(entity, userId);
        operationLogService.record(userId, "图片作品", "编辑", "编辑作品：" + entity.getTitle());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertCreator(role);
        imageAssetService.removeById(id);
        operationLogService.record(userId, "图片作品", "删除", "删除作品：" + id);
        return Result.success();
    }
}
