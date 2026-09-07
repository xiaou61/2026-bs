package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.Review;
import com.xiaou.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "评价管理")
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "添加评价")
    @PostMapping("/add")
    public Result<Void> add(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam Long bookingId,
                            @RequestParam Integer rating,
                            @RequestParam(required = false) String content,
                            @RequestParam(required = false) String images) {
        Long userId = Long.parseLong(userDetails.getUsername());
        reviewService.createReview(userId, bookingId, rating, content, images);
        return Result.success();
    }

    @Operation(summary = "民宿评价列表")
    @GetMapping("/homestay/{homestayId}")
    public Result<IPage<Review>> listByHomestay(@PathVariable Long homestayId,
                                                @RequestParam(defaultValue = "1") Integer current,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(reviewService.pageByHomestay(homestayId, current, size));
    }

    @Operation(summary = "我的评价列表")
    @GetMapping("/my")
    public Result<IPage<Review>> myReviews(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestParam(defaultValue = "1") Integer current,
                                           @RequestParam(defaultValue = "10") Integer size) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return Result.success(reviewService.pageByUser(userId, current, size));
    }
}
