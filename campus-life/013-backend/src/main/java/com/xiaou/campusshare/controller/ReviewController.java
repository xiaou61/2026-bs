package com.xiaou.campusshare.controller;

import com.xiaou.campusshare.common.Result;
import com.xiaou.campusshare.entity.Review;
import com.xiaou.campusshare.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/submit")
    public Result<?> submit(HttpServletRequest request, @RequestBody Review review) {
        Long userId = (Long) request.getAttribute("userId");
        review.setReviewerId(userId);
        reviewService.save(review);
        return Result.success("评价成功");
    }

    @GetMapping("/list")
    public Result<List<Review>> getList(@RequestParam Long targetId, @RequestParam String reviewType) {
        List<Review> reviews = reviewService.getReviewsByTarget(targetId, reviewType);
        return Result.success(reviews);
    }
}

