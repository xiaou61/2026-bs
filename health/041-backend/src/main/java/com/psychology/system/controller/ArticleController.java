package com.psychology.system.controller;

import com.psychology.system.common.Result;
import com.psychology.system.entity.Article;
import com.psychology.system.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    public Result<List<Article>> getArticles(@RequestParam(required = false) String category) {
        return Result.success(articleService.getArticles(category));
    }

    @GetMapping("/{id}")
    public Result<Article> getArticleById(@PathVariable Long id) {
        return Result.success(articleService.getArticleById(id));
    }
}
