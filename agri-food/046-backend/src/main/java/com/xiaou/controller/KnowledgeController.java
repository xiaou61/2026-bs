package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.Knowledge;
import com.xiaou.mapper.KnowledgeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "环保知识")
@RestController
@RequestMapping("/api/knowledge")
@RequiredArgsConstructor
public class KnowledgeController {

    private final KnowledgeMapper knowledgeMapper;

    @Operation(summary = "知识列表")
    @GetMapping("/list")
    public Result<IPage<Knowledge>> list(@RequestParam(defaultValue = "1") Integer current,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         @RequestParam(required = false) Integer category) {
        LambdaQueryWrapper<Knowledge> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Knowledge::getStatus, 1);
        if (category != null) {
            wrapper.eq(Knowledge::getCategory, category);
        }
        wrapper.orderByDesc(Knowledge::getPublishTime);
        return Result.success(knowledgeMapper.selectPage(new Page<>(current, size), wrapper));
    }

    @Operation(summary = "知识详情")
    @GetMapping("/{id}")
    public Result<Knowledge> detail(@PathVariable Long id) {
        Knowledge knowledge = knowledgeMapper.selectById(id);
        if (knowledge != null) {
            knowledge.setViewCount(knowledge.getViewCount() + 1);
            knowledgeMapper.updateById(knowledge);
        }
        return Result.success(knowledge);
    }
}
