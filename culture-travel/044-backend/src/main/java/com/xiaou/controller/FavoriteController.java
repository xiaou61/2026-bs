package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.Homestay;
import com.xiaou.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "收藏管理")
@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "添加收藏")
    @PostMapping("/add/{homestayId}")
    public Result<Void> add(@AuthenticationPrincipal UserDetails userDetails,
                            @PathVariable Long homestayId) {
        Long userId = Long.parseLong(userDetails.getUsername());
        favoriteService.addFavorite(userId, homestayId);
        return Result.success();
    }

    @Operation(summary = "取消收藏")
    @DeleteMapping("/remove/{homestayId}")
    public Result<Void> remove(@AuthenticationPrincipal UserDetails userDetails,
                               @PathVariable Long homestayId) {
        Long userId = Long.parseLong(userDetails.getUsername());
        favoriteService.removeFavorite(userId, homestayId);
        return Result.success();
    }

    @Operation(summary = "是否已收藏")
    @GetMapping("/check/{homestayId}")
    public Result<Boolean> check(@AuthenticationPrincipal UserDetails userDetails,
                                 @PathVariable Long homestayId) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return Result.success(favoriteService.isFavorite(userId, homestayId));
    }

    @Operation(summary = "我的收藏列表")
    @GetMapping("/my")
    public Result<IPage<Homestay>> myFavorites(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestParam(defaultValue = "1") Integer current,
                                               @RequestParam(defaultValue = "10") Integer size) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return Result.success(favoriteService.pageFavorites(userId, current, size));
    }
}
