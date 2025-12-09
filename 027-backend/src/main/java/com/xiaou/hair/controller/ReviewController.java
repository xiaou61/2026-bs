package com.xiaou.hair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.hair.common.Result;
import com.xiaou.hair.entity.Review;
import com.xiaou.hair.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评价控制器
 */
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * 创建评价
     */
    @PostMapping("/create")
    public Result<Void> createReview(@RequestBody Review review) {
        reviewService.createReview(review);
        return Result.success("评价成功");
    }

    /**
     * 我的评价列表
     */
    @GetMapping("/my-list")
    public Result<Page<Review>> getMyReviews(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Review> page = reviewService.getMyReviews(pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 理发师的评价列表
     */
    @GetMapping("/hairdresser/{id}")
    public Result<Page<Review>> getHairdresserReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Review> page = reviewService.getHairdresserReviews(id, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 门店的评价列表
     */
    @GetMapping("/store/{id}")
    public Result<Page<Review>> getStoreReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Review> page = reviewService.getStoreReviews(id, pageNum, pageSize);
        return Result.success(page);
    }
}
