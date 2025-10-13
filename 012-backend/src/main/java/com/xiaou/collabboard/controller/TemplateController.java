package com.xiaou.collabboard.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.collabboard.entity.Template;
import com.xiaou.collabboard.service.TemplateService;
import com.xiaou.collabboard.util.Result;
import com.xiaou.collabboard.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @PostMapping("/create")
    public Result<Template> createTemplate(@RequestBody Map<String, Object> params) {
        Long userId = UserHolder.get();
        Long teamId = params.containsKey("teamId") ? Long.parseLong(params.get("teamId").toString()) : null;
        String templateName = (String) params.get("templateName");
        String templateType = (String) params.get("templateType");
        String category = (String) params.get("category");
        String content = (String) params.get("content");

        Template template = templateService.createTemplate(userId, teamId, templateName, templateType, category, content);
        return Result.success(template);
    }

    @GetMapping("/{id}")
    public Result<Template> getTemplate(@PathVariable Long id) {
        Template template = templateService.getById(id);
        return Result.success(template);
    }

    @PutMapping("/{id}")
    public Result<Void> updateTemplate(@PathVariable Long id, @RequestBody Template template) {
        template.setId(id);
        templateService.updateById(template);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteTemplate(@PathVariable Long id) {
        templateService.removeById(id);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<IPage<Template>> getTemplateList(
            @RequestParam(required = false) String templateType,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer isPublic,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "20") Integer pageSize) {

        IPage<Template> page = templateService.getTemplateList(templateType, category, isPublic, pageNum, pageSize);
        return Result.success(page);
    }

    @PostMapping("/{id}/use")
    public Result<Void> useTemplate(@PathVariable Long id) {
        templateService.incrementUseCount(id);
        return Result.success();
    }
}

