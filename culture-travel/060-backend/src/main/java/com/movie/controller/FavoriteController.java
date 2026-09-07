package com.movie.controller;

import com.movie.common.Result;
import com.movie.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Resource
    private FavoriteService favoriteService;

    @PostMapping("/{movieId}")
    public Result<?> toggle(@PathVariable Long movieId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        favoriteService.toggle(userId, movieId);
        return Result.success();
    }

    @GetMapping("/my")
    public Result<?> myFavorites(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(favoriteService.getMyFavorites(userId));
    }

    @GetMapping("/check/{movieId}")
    public Result<?> check(@PathVariable Long movieId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(favoriteService.isFavorite(userId, movieId));
    }
}
