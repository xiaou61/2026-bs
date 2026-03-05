package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.entity.News;
import com.alumni.entity.NewsComment;
import com.alumni.service.NewsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/list")
    public Result<Page<News>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   String category, String title) {
        return Result.success(newsService.list(pageNum, pageSize, category, title));
    }

    @GetMapping("/{id}")
    public Result<News> getById(@PathVariable Long id) {
        return Result.success(newsService.getById(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody News news, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        news.setAuthorId(userId);
        newsService.add(news);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody News news) {
        newsService.update(news);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        newsService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}/comments")
    public Result<List<NewsComment>> getComments(@PathVariable Long id) {
        return Result.success(newsService.getComments(id));
    }

    @PostMapping("/{id}/comment")
    public Result<?> addComment(@PathVariable Long id, @RequestBody NewsComment comment, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        comment.setNewsId(id);
        comment.setUserId(userId);
        newsService.addComment(comment);
        return Result.success();
    }

    @DeleteMapping("/comment/{id}")
    public Result<?> deleteComment(@PathVariable Long id) {
        newsService.deleteComment(id);
        return Result.success();
    }
}
