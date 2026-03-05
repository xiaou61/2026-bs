package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public Result<?> add(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        Long userId = (Long) request.getAttribute("userId");
        Long orderId = Long.valueOf(params.get("orderId").toString());
        Integer rating = (Integer) params.get("rating");
        String content = (String) params.get("content");
        String images = (String) params.get("images");
        reviewService.add(userId, orderId, rating, content, images);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Long productId, Long merchantId, Integer status) {
        return Result.success(reviewService.page(pageNum, pageSize, productId, merchantId, status));
    }

    @PutMapping("/audit/{id}")
    public Result<?> audit(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        reviewService.audit(id, params.get("status"));
        return Result.success();
    }

    @GetMapping("/product/{productId}")
    public Result<?> productReviews(@PathVariable Long productId,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(reviewService.productReviews(productId, pageNum, pageSize));
    }
}
