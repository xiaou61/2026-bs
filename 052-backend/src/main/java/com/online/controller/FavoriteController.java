package com.online.controller;

import com.online.common.Result;
import com.online.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/add")
    public Result<?> addFavorite(HttpServletRequest request, @RequestBody Map<String, Long> params) {
        Long userId = (Long) request.getAttribute("userId");
        Long courseId = params.get("courseId");
        favoriteService.addFavorite(userId, courseId);
        return Result.success();
    }

    @DeleteMapping("/{courseId}")
    public Result<?> removeFavorite(HttpServletRequest request, @PathVariable Long courseId) {
        Long userId = (Long) request.getAttribute("userId");
        favoriteService.removeFavorite(userId, courseId);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> getList(HttpServletRequest request,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(favoriteService.getList(userId, pageNum, pageSize));
    }

    @GetMapping("/check/{courseId}")
    public Result<?> checkFavorite(HttpServletRequest request, @PathVariable Long courseId) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(favoriteService.checkFavorite(userId, courseId));
    }
}
