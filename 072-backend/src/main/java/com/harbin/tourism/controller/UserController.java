package com.harbin.tourism.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.Result;
import com.harbin.tourism.entity.Favorite;
import com.harbin.tourism.entity.User;
import com.harbin.tourism.service.FavoriteService;
import com.harbin.tourism.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/info")
    public Result<User> getInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(userService.getById(userId));
    }

    @PutMapping("/info")
    public Result<Void> updateInfo(@RequestBody User user, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        user.setId(userId);
        userService.update(user);
        return Result.success();
    }

    @PostMapping("/recharge")
    public Result<Void> recharge(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        BigDecimal amount = new BigDecimal(params.get("amount").toString());
        userService.recharge(userId, amount);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<Page<User>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        return Result.success(userService.page(pageNum, pageSize, keyword));
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        userService.updateStatus(id, params.get("status"));
        return Result.success();
    }

    @PostMapping("/favorite")
    public Result<Void> addFavorite(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long targetId = Long.valueOf(params.get("targetId").toString());
        String targetType = (String) params.get("targetType");
        favoriteService.add(userId, targetId, targetType);
        return Result.success();
    }

    @DeleteMapping("/favorite")
    public Result<Void> removeFavorite(
            @RequestParam Long targetId,
            @RequestParam String targetType,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        favoriteService.remove(userId, targetId, targetType);
        return Result.success();
    }

    @GetMapping("/favorite/check")
    public Result<Boolean> isFavorite(
            @RequestParam Long targetId,
            @RequestParam String targetType,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(favoriteService.isFavorite(userId, targetId, targetType));
    }

    @GetMapping("/favorites")
    public Result<List<Favorite>> getFavorites(
            @RequestParam(required = false) String targetType,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(favoriteService.getUserFavorites(userId, targetType));
    }
}
