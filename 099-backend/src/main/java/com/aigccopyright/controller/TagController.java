package com.aigccopyright.controller;

import com.aigccopyright.common.Result;
import com.aigccopyright.entity.RiskTag;
import com.aigccopyright.service.AuthService;
import com.aigccopyright.service.OperationLogService;
import com.aigccopyright.service.RiskTagService;
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
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private RiskTagService riskTagService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<RiskTag>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      String keyword,
                                      String tagType,
                                      Integer status) {
        return Result.success(riskTagService.page(pageNum, pageSize, keyword, tagType, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody RiskTag entity) {
        authService.assertAdmin(role);
        riskTagService.saveEntity(entity);
        operationLogService.record(userId, "风险标签", "新增", "新增标签：" + entity.getTagName());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody RiskTag entity) {
        authService.assertAdmin(role);
        riskTagService.saveEntity(entity);
        operationLogService.record(userId, "风险标签", "编辑", "编辑标签：" + entity.getTagName());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        riskTagService.removeById(id);
        operationLogService.record(userId, "风险标签", "删除", "删除标签：" + id);
        return Result.success();
    }
}
