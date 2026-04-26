package com.football.controller;

import com.football.common.Result;
import com.football.entity.NewsNotice;
import com.football.service.NewsService;
import com.football.utils.AuthUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Resource
    private NewsService newsService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String title,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(newsService.page(pageNum, pageSize, title, status));
    }

    @GetMapping("/public/list")
    public Result<?> publicList() {
        return Result.success(newsService.publicList());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody NewsNotice notice, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        Long userId = (Long) request.getAttribute("userId");
        newsService.save(notice, userId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        newsService.deleteById(id);
        return Result.success();
    }
}
