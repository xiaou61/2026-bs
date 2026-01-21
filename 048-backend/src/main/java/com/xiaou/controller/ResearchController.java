package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.service.ResearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/research")
@RequiredArgsConstructor
public class ResearchController {
    private final ResearchService researchService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int current,
                          @RequestParam(defaultValue = "10") int size,
                          String keyword) {
        return Result.success(researchService.page(current, size, keyword, 1));
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return Result.success(researchService.getById(id));
    }
}
