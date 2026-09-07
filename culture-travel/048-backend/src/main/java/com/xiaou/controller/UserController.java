package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.Favorite;
import com.xiaou.entity.User;
import com.xiaou.mapper.FavoriteMapper;
import com.xiaou.mapper.RelicMapper;
import com.xiaou.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FavoriteMapper favoriteMapper;
    private final RelicMapper relicMapper;

    @GetMapping("/info")
    public Result<?> info(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getById(userId);
        user.setPassword(null);
        return Result.success(user);
    }

    @GetMapping("/favorites")
    public Result<?> favorites(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<Favorite> list = favoriteMapper.selectList(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId));
        list.forEach(f -> {
            var relic = relicMapper.selectById(f.getRelicId());
            if (relic != null) f.setRelicName(relic.getName());
        });
        return Result.success(list);
    }

    @PostMapping("/favorite")
    public Result<?> addFavorite(HttpServletRequest request, @RequestBody Favorite favorite) {
        Long userId = (Long) request.getAttribute("userId");
        favorite.setUserId(userId);
        favoriteMapper.insert(favorite);
        return Result.success();
    }

    @DeleteMapping("/favorite/{relicId}")
    public Result<?> removeFavorite(HttpServletRequest request, @PathVariable Long relicId) {
        Long userId = (Long) request.getAttribute("userId");
        favoriteMapper.delete(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId).eq(Favorite::getRelicId, relicId));
        return Result.success();
    }
}
