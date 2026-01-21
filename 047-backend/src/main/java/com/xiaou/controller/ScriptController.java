package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.Script;
import com.xiaou.entity.ScriptCategory;
import com.xiaou.entity.ScriptContent;
import com.xiaou.mapper.ScriptCategoryMapper;
import com.xiaou.mapper.ScriptContentMapper;
import com.xiaou.service.ScriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/script")
@RequiredArgsConstructor
public class ScriptController {

    private final ScriptService scriptService;
    private final ScriptCategoryMapper categoryMapper;
    private final ScriptContentMapper contentMapper;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") int current,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(required = false) Long categoryId,
                       @RequestParam(required = false) Integer type,
                       @RequestParam(required = false) Integer difficulty,
                       @RequestParam(required = false) String keyword) {
        return Result.success(scriptService.pageScripts(current, size, categoryId, type, difficulty, keyword));
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Script script = scriptService.getById(id);
        if (script != null) {
            script.setViewCount(script.getViewCount() + 1);
            scriptService.updateById(script);
        }
        return Result.success(script);
    }

    @GetMapping("/{id}/content")
    public Result content(@PathVariable Long id) {
        return Result.success(contentMapper.selectList(
                new LambdaQueryWrapper<ScriptContent>()
                        .eq(ScriptContent::getScriptId, id)
                        .orderByAsc(ScriptContent::getSort)));
    }

    @GetMapping("/categories")
    public Result categories() {
        return Result.success(categoryMapper.selectList(
                new LambdaQueryWrapper<ScriptCategory>()
                        .eq(ScriptCategory::getStatus, 1)
                        .orderByAsc(ScriptCategory::getSort)));
    }

    @PostMapping("/{id}/like")
    public Result like(@PathVariable Long id) {
        Script script = scriptService.getById(id);
        if (script != null) {
            script.setLikeCount(script.getLikeCount() + 1);
            scriptService.updateById(script);
        }
        return Result.success();
    }
}
