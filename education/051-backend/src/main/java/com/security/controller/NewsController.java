package com.security.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.security.common.Result;
import com.security.entity.News;
import com.security.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/list")
    public Result<Page<News>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(newsService.getNewsList(page, size));
    }

    @GetMapping("/{id}")
    public Result<News> detail(@PathVariable Long id) {
        return Result.success(newsService.getNewsDetail(id));
    }
}
