package com.harbin.tourism.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.Result;
import com.harbin.tourism.entity.Review;
import com.harbin.tourism.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/list")
    public Result<Page<Review>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String targetType,
            @RequestParam(required = false) Long targetId) {
        return Result.success(reviewService.page(pageNum, pageSize, targetType, targetId));
    }

    @GetMapping("/target")
    public Result<List<Review>> getByTarget(
            @RequestParam String targetType,
            @RequestParam Long targetId) {
        return Result.success(reviewService.getByTarget(targetType, targetId));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Review review, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        review.setUserId(userId);
        reviewService.save(review);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Review review) {
        reviewService.updateStatus(id, review.getStatus());
        return Result.success();
    }
}
