package com.xiaou.express.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.express.common.Result;
import com.xiaou.express.dto.ReviewRequest;
import com.xiaou.express.entity.Review;
import com.xiaou.express.service.ReviewService;
import com.xiaou.express.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public Result<Void> createReview(@RequestBody ReviewRequest request) {
        Long userId = UserContext.getCurrentUserId();
        reviewService.createReview(userId, request);
        return Result.success();
    }

    @GetMapping("/user/{userId}")
    public Result<Page<Review>> getUserReviews(@PathVariable Long userId,
                                                 @RequestParam(defaultValue = "1") int pageNum,
                                                 @RequestParam(defaultValue = "10") int pageSize) {
        Page<Review> page = reviewService.getUserReviews(userId, pageNum, pageSize);
        return Result.success(page);
    }
}

