package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.Favorite;
import com.xiaou.mapper.FavoriteMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteMapper favoriteMapper;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int current,
                         @RequestParam(defaultValue = "10") int size,
                         @RequestParam(required = false) Integer targetType,
                         HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId);
        if (targetType != null) wrapper.eq(Favorite::getTargetType, targetType);
        wrapper.orderByDesc(Favorite::getCreateTime);
        return Result.success(favoriteMapper.selectPage(new Page<>(current, size), wrapper));
    }

    @PostMapping
    public Result<?> add(@RequestParam Integer targetType, @RequestParam Long targetId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        // 检查是否已收藏
        Long count = favoriteMapper.selectCount(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .eq(Favorite::getTargetType, targetType)
                .eq(Favorite::getTargetId, targetId));
        if (count > 0) {
            return Result.error("已收藏");
        }
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setTargetType(targetType);
        favorite.setTargetId(targetId);
        favoriteMapper.insert(favorite);
        return Result.success();
    }

    @DeleteMapping
    public Result<?> remove(@RequestParam Integer targetType, @RequestParam Long targetId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        favoriteMapper.delete(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .eq(Favorite::getTargetType, targetType)
                .eq(Favorite::getTargetId, targetId));
        return Result.success();
    }

    @GetMapping("/check")
    public Result<?> check(@RequestParam Integer targetType, @RequestParam Long targetId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long count = favoriteMapper.selectCount(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .eq(Favorite::getTargetType, targetType)
                .eq(Favorite::getTargetId, targetId));
        return Result.success(count > 0);
    }
}
