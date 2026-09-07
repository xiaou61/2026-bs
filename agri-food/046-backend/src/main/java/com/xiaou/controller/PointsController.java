package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.ExchangeRecord;
import com.xiaou.entity.PointsProduct;
import com.xiaou.entity.PointsRecord;
import com.xiaou.entity.User;
import com.xiaou.service.PointsService;
import com.xiaou.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "积分管理")
@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointsController {

    private final PointsService pointsService;
    private final UserService userService;

    @Operation(summary = "我的积分")
    @GetMapping("/my")
    public Result<Integer> myPoints(@AuthenticationPrincipal Long userId) {
        User user = userService.getById(userId);
        return Result.success(user.getPoints());
    }

    @Operation(summary = "积分记录")
    @GetMapping("/records")
    public Result<IPage<PointsRecord>> records(@AuthenticationPrincipal Long userId,
                                               @RequestParam(defaultValue = "1") Integer current,
                                               @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(pointsService.getPointsRecords(userId, current, size));
    }

    @Operation(summary = "积分商品列表")
    @GetMapping("/products")
    public Result<IPage<PointsProduct>> products(@RequestParam(defaultValue = "1") Integer current,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(pointsService.getProducts(current, size));
    }

    @Operation(summary = "兑换商品")
    @PostMapping("/exchange")
    public Result<Void> exchange(@AuthenticationPrincipal Long userId,
                                 @RequestParam Long productId,
                                 @RequestParam(defaultValue = "1") Integer quantity) {
        pointsService.exchange(userId, productId, quantity);
        return Result.success();
    }

    @Operation(summary = "兑换记录")
    @GetMapping("/exchange/records")
    public Result<IPage<ExchangeRecord>> exchangeRecords(@AuthenticationPrincipal Long userId,
                                                         @RequestParam(defaultValue = "1") Integer current,
                                                         @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(pointsService.getExchangeRecords(userId, current, size));
    }
}
