package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.entity.Merchant;
import com.groupbuy.service.MerchantService;
import com.groupbuy.service.ReviewService;
import com.groupbuy.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MerchantService merchantService;

    @PostMapping("/add")
    public Result<?> add(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        AuthUtils.requireUser(request);
        Long userId = AuthUtils.getUserId(request);
        Long orderId = Long.valueOf(params.get("orderId").toString());
        Integer rating = (Integer) params.get("rating");
        String content = (String) params.get("content");
        String images = (String) params.get("images");
        reviewService.add(userId, orderId, rating, content, images);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<?> page(HttpServletRequest request,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Long productId, Long merchantId, Integer status) {
        Integer role = AuthUtils.getRole(request);
        if (role == 1) {
            Merchant merchant = merchantService.requireApprovedMerchant(AuthUtils.getUserId(request));
            merchantId = merchant.getId();
        } else if (role != 0) {
            AuthUtils.requireAdmin(request);
        }
        return Result.success(reviewService.page(pageNum, pageSize, productId, merchantId, status));
    }

    @PutMapping("/audit/{id}")
    public Result<?> audit(HttpServletRequest request, @PathVariable Long id, @RequestBody Map<String, Integer> params) {
        AuthUtils.requireAdmin(request);
        reviewService.audit(id, params.get("status"));
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(HttpServletRequest request, @PathVariable Long id) {
        AuthUtils.requireAdmin(request);
        reviewService.delete(id);
        return Result.success();
    }

    @GetMapping("/product/{productId}")
    public Result<?> productReviews(@PathVariable Long productId,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(reviewService.productReviews(productId, pageNum, pageSize));
    }
}
