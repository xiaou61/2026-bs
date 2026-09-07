package com.enterprise.controller;

import com.enterprise.common.Result;
import com.enterprise.service.FavoriteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Resource
    private FavoriteService favoriteService;

    @PostMapping("/{serviceId}")
    public Result<?> toggle(@PathVariable Long serviceId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(favoriteService.toggle(userId, serviceId));
    }

    @GetMapping("/my")
    public Result<?> my(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(favoriteService.myFavorites(userId));
    }

    @GetMapping("/check/{serviceId}")
    public Result<?> check(@PathVariable Long serviceId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(favoriteService.check(userId, serviceId));
    }
}





