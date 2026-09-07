package com.xiaou.campusshare.controller;

import com.xiaou.campusshare.common.Result;
import com.xiaou.campusshare.service.FavoriteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/toggle")
    public Result<?> toggle(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Long userId = (Long) request.getAttribute("userId");
        Long targetId = Long.parseLong(params.get("targetId"));
        String targetType = params.get("targetType");
        boolean before = favoriteService.isFavorited(userId, targetId, targetType);
        favoriteService.toggleFavorite(userId, targetId, targetType);
        return Result.success(before ? "已取消收藏" : "收藏成功");
    }

    @GetMapping("/status")
    public Result<Boolean> getStatus(
            HttpServletRequest request,
            @RequestParam Long targetId,
            @RequestParam String targetType) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(favoriteService.isFavorited(userId, targetId, targetType));
    }

    @GetMapping("/list")
    public Result<List<Map<String, Object>>> getList(
            HttpServletRequest request,
            @RequestParam String targetType) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(favoriteService.getFavoriteItems(userId, targetType));
    }
}
