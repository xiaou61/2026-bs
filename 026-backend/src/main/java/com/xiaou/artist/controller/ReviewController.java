package com.xiaou.artist.controller;

import com.xiaou.artist.common.Result;
import com.xiaou.artist.entity.Review;
import com.xiaou.artist.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;
    
    @PostMapping("/add")
    public Result<Review> addReview(@RequestBody Review review) {
        Review newReview = reviewService.addReview(review);
        return Result.success(newReview);
    }
    
    @GetMapping("/order/{orderId}")
    public Result<List<Review>> getReviewsByOrderId(@PathVariable Long orderId) {
        List<Review> reviews = reviewService.getReviewsByOrderId(orderId);
        return Result.success(reviews);
    }
    
    @GetMapping("/user/{toUserId}")
    public Result<List<Review>> getReviewsByToUserId(@PathVariable Long toUserId) {
        List<Review> reviews = reviewService.getReviewsByToUserId(toUserId);
        return Result.success(reviews);
    }
}
