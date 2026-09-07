package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.Favorite;
import com.xiaou.entity.Review;
import com.xiaou.mapper.FavoriteMapper;
import com.xiaou.mapper.ReviewMapper;
import com.xiaou.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FavoriteMapper favoriteMapper;
    private final ReviewMapper reviewMapper;

    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(userService.getById(userId));
    }

    @GetMapping("/favorites")
    public Result favorites(HttpServletRequest request, @RequestParam(required = false) Integer type) {
        Long userId = (Long) request.getAttribute("userId");
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId);
        if (type != null) wrapper.eq(Favorite::getType, type);
        return Result.success(favoriteMapper.selectList(wrapper));
    }

    @PostMapping("/favorite")
    public Result addFavorite(HttpServletRequest request, @RequestBody Favorite fav) {
        Long userId = (Long) request.getAttribute("userId");
        long count = favoriteMapper.selectCount(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .eq(Favorite::getType, fav.getType())
                .eq(Favorite::getTargetId, fav.getTargetId()));
        if (count == 0) {
            fav.setUserId(userId);
            favoriteMapper.insert(fav);
        }
        return Result.success();
    }

    @DeleteMapping("/favorite/{id}")
    public Result removeFavorite(@PathVariable Long id) {
        favoriteMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/review")
    public Result addReview(HttpServletRequest request, @RequestBody Review review) {
        Long userId = (Long) request.getAttribute("userId");
        review.setUserId(userId);
        reviewMapper.insert(review);
        return Result.success();
    }

    @GetMapping("/reviews")
    public Result myReviews(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(reviewMapper.selectList(
                new LambdaQueryWrapper<Review>().eq(Review::getUserId, userId).orderByDesc(Review::getCreateTime)));
    }
}
