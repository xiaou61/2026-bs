package com.xiaou.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.PageResult;
import com.xiaou.common.Result;
import com.xiaou.service.FavoriteService;
import com.xiaou.vo.FavoriteVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/add")
    public Result<String> addFavorite(@RequestAttribute("userId") Long userId,
                                      @RequestBody Map<String, Long> payload) {
        favoriteService.addFavorite(userId, payload.get("productId"));
        return Result.success("收藏成功");
    }

    @DeleteMapping("/{productId}")
    public Result<String> removeFavorite(@RequestAttribute("userId") Long userId,
                                         @PathVariable Long productId) {
        favoriteService.removeFavorite(userId, productId);
        return Result.success("取消收藏成功");
    }

    @GetMapping("/my")
    public Result<PageResult<FavoriteVO>> getMyFavorites(@RequestAttribute("userId") Long userId,
                                                         @RequestParam(defaultValue = "1") Integer current,
                                                         @RequestParam(defaultValue = "10") Integer size) {
        Page<FavoriteVO> page = favoriteService.getMyFavorites(userId, current, size);
        return Result.success(PageResult.of(page.getTotal(), page.getRecords(), page.getCurrent(), page.getSize()));
    }

    @GetMapping("/check/{productId}")
    public Result<Boolean> checkFavorite(@RequestAttribute("userId") Long userId,
                                         @PathVariable Long productId) {
        return Result.success(favoriteService.checkFavorite(userId, productId));
    }
}
