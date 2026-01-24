package com.security.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.security.common.Result;
import com.security.service.ArticleService;
import com.security.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/add")
    public Result<?> add(@RequestParam Long articleId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        articleService.addFavorite(articleId, userId);
        return Result.success();
    }

    @DeleteMapping("/{articleId}")
    public Result<?> remove(@PathVariable Long articleId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        articleService.removeFavorite(articleId, userId);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<Page<ArticleVO>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(articleService.getFavoriteList(userId, page, size));
    }
}
