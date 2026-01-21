package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.dto.ScriptDTO;
import com.xiaou.entity.Script;
import com.xiaou.service.ScriptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
public class AuthorController {

    private final ScriptService scriptService;

    @GetMapping("/scripts")
    public Result myScripts(HttpServletRequest request,
                            @RequestParam(defaultValue = "1") int current,
                            @RequestParam(defaultValue = "10") int size) {
        Long userId = (Long) request.getAttribute("userId");
        Page<Script> page = scriptService.page(new Page<>(current, size),
                new LambdaQueryWrapper<Script>()
                        .eq(Script::getAuthorId, userId)
                        .orderByDesc(Script::getCreateTime));
        return Result.success(page);
    }

    @PostMapping("/script")
    public Result createScript(HttpServletRequest request, @RequestBody ScriptDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        scriptService.createScript(userId, dto);
        return Result.success();
    }

    @PutMapping("/script")
    public Result updateScript(HttpServletRequest request, @RequestBody ScriptDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        scriptService.updateScript(userId, dto);
        return Result.success();
    }

    @GetMapping("/statistics")
    public Result statistics(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        long total = scriptService.count(new LambdaQueryWrapper<Script>().eq(Script::getAuthorId, userId));
        long online = scriptService.count(new LambdaQueryWrapper<Script>().eq(Script::getAuthorId, userId).eq(Script::getStatus, 1));
        long pending = scriptService.count(new LambdaQueryWrapper<Script>().eq(Script::getAuthorId, userId).eq(Script::getStatus, 0));
        return Result.success(java.util.Map.of("total", total, "online", online, "pending", pending));
    }
}
