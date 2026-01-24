package com.security.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.security.common.Result;
import com.security.service.ArticleService;
import com.security.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public Result<Page<ArticleVO>> list(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(articleService.getArticleList(categoryId, page, size));
    }

    @GetMapping("/{id}")
    public Result<ArticleVO> detail(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(articleService.getArticleDetail(id, userId));
    }

    @PostMapping("/like/{id}")
    public Result<?> like(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        articleService.likeArticle(id, userId);
        return Result.success();
    }

    @PostMapping("/learn")
    public Result<?> recordLearn(@RequestParam Long articleId, @RequestParam Integer progress, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        articleService.recordLearn(articleId, userId, progress);
        return Result.success();
    }
}
