package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Favorite;
import com.xiaou.entity.User;
import com.xiaou.service.FavoriteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping
    public Result<?> addFavorite(@RequestBody Favorite favorite, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            favorite.setUserId(user.getId());
            favoriteService.addFavorite(favorite);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping
    public Result<?> removeFavorite(@RequestParam String itemType, @RequestParam Long itemId, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            favoriteService.removeFavorite(user.getId(), itemType, itemId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> getFavorites(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map<String, Object> favorites = favoriteService.getUserFavorites(user.getId());
        return Result.success(favorites);
    }
}

