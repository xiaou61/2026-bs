package com.harbin.tourism.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.Result;
import com.harbin.tourism.entity.Favorite;
import com.harbin.tourism.entity.User;
import com.harbin.tourism.service.FavoriteService;
import com.harbin.tourism.service.UserService;
import com.harbin.tourism.utils.AuthUtils;
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
        Long userId = AuthUtils.currentUserId(request);
        return Result.success(userService.getById(userId));
    }

    @PutMapping("/info")
    public Result<Void> updateInfo(@RequestBody User user, HttpServletRequest request) {
        Long userId = AuthUtils.currentUserId(request);
        userService.updateProfile(userId, user);
        return Result.success();
    }

    @PutMapping("/password")
    public Result<Void> changePassword(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Long userId = AuthUtils.currentUserId(request);
        userService.changePassword(userId, params.get("oldPassword"), params.get("newPassword"));
        return Result.success();
    }

    @PostMapping("/recharge")
    public Result<Void> recharge(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long userId = AuthUtils.currentUserId(request);
        BigDecimal amount = new BigDecimal(params.get("amount").toString());
        userService.recharge(userId, amount);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<Page<User>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        return Result.success(userService.page(pageNum, pageSize, keyword));
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        userService.updateStatus(id, params.get("status"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        userService.delete(id);
        return Result.success();
    }

    @PostMapping("/favorite")
    public Result<Void> addFavorite(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long userId = AuthUtils.currentUserId(request);
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
        Long userId = AuthUtils.currentUserId(request);
        favoriteService.remove(userId, targetId, targetType);
        return Result.success();
    }

    @GetMapping("/favorite/check")
    public Result<Boolean> isFavorite(
            @RequestParam Long targetId,
            @RequestParam String targetType,
            HttpServletRequest request) {
        Long userId = AuthUtils.currentUserId(request);
        return Result.success(favoriteService.isFavorite(userId, targetId, targetType));
    }

    @GetMapping("/favorites")
    public Result<List<Favorite>> getFavorites(
            @RequestParam(required = false) String targetType,
            HttpServletRequest request) {
        Long userId = AuthUtils.currentUserId(request);
        return Result.success(favoriteService.getUserFavorites(userId, targetType));
    }
}
