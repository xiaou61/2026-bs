package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.service.RelicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relic")
@RequiredArgsConstructor
public class RelicController {
    private final RelicService relicService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int current,
                          @RequestParam(defaultValue = "10") int size,
                          String keyword, Long categoryId, Long dynastyId, Integer level) {
        return Result.success(relicService.page(current, size, keyword, categoryId, dynastyId, level));
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        relicService.addViewCount(id);
        return Result.success(relicService.getById(id));
    }

    @GetMapping("/{id}/images")
    public Result<?> images(@PathVariable Long id) {
        return Result.success(relicService.getImages(id));
    }

    @PostMapping("/{id}/like")
    public Result<?> like(@PathVariable Long id) {
        relicService.addLikeCount(id);
        return Result.success();
    }
}
