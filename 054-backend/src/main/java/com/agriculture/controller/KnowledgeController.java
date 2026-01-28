package com.agriculture.controller;

import com.agriculture.common.Result;
import com.agriculture.entity.Knowledge;
import com.agriculture.service.KnowledgeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;

    @GetMapping("/page")
    public Result<Page<Knowledge>> getPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                            @RequestParam(required = false) String title,
                                            @RequestParam(required = false) String category) {
        return Result.success(knowledgeService.getPage(pageNum, pageSize, title, category));
    }

    @GetMapping("/{id}")
    public Result<Knowledge> getById(@PathVariable Long id) {
        knowledgeService.incrementView(id);
        return Result.success(knowledgeService.getById(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody Knowledge knowledge, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        knowledge.setAuthorId(userId);
        knowledgeService.save(knowledge);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Knowledge knowledge) {
        knowledgeService.updateById(knowledge);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        knowledgeService.removeById(id);
        return Result.success();
    }

    @PostMapping("/{id}/like")
    public Result<?> like(@PathVariable Long id) {
        knowledgeService.like(id);
        return Result.success();
    }

    @PostMapping("/{id}/collect")
    public Result<?> collect(@PathVariable Long id) {
        knowledgeService.collect(id);
        return Result.success();
    }
}
