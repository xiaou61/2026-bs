package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.Review;
import com.xiaou.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewMapper reviewMapper;

    @GetMapping("/shop/{shopId}")
    public Result shopReviews(@PathVariable Long shopId,
                              @RequestParam(defaultValue = "1") int current,
                              @RequestParam(defaultValue = "10") int size) {
        return Result.success(reviewMapper.selectPage(new Page<>(current, size),
                new LambdaQueryWrapper<Review>()
                        .eq(Review::getShopId, shopId)
                        .orderByDesc(Review::getCreateTime)));
    }

    @GetMapping("/script/{scriptId}")
    public Result scriptReviews(@PathVariable Long scriptId,
                                @RequestParam(defaultValue = "1") int current,
                                @RequestParam(defaultValue = "10") int size) {
        return Result.success(reviewMapper.selectPage(new Page<>(current, size),
                new LambdaQueryWrapper<Review>()
                        .eq(Review::getScriptId, scriptId)
                        .orderByDesc(Review::getCreateTime)));
    }
}
