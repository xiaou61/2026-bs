package com.movie.controller;

import com.movie.common.Result;
import com.movie.entity.Review;
import com.movie.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Resource
    private ReviewService reviewService;

    @GetMapping("/movie/{movieId}")
    public Result<?> getByMovieId(@PathVariable Long movieId) {
        return Result.success(reviewService.getByMovieId(movieId));
    }

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String movieTitle) {
        return Result.success(reviewService.getPage(pageNum, pageSize, movieTitle));
    }

    @PostMapping
    public Result<?> add(@RequestBody Review review, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        review.setUserId(userId);
        reviewService.add(review);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return Result.success();
    }
}
