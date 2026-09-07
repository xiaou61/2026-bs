package com.security.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.security.common.Result;
import com.security.dto.AdminLoginDTO;
import com.security.entity.*;
import com.security.service.AdminService;
import com.security.vo.LoginVO;
import com.security.vo.StatsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody AdminLoginDTO dto) {
        return Result.success(adminService.login(dto));
    }

    @GetMapping("/stats")
    public Result<StatsVO> getStats() {
        return Result.success(adminService.getStats());
    }

    @GetMapping("/user/list")
    public Result<Page<User>> getUserList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        return Result.success(adminService.getUserList(page, size, keyword));
    }

    @PutMapping("/user/status/{id}")
    public Result<?> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        adminService.updateUserStatus(id, status);
        return Result.success();
    }

    @GetMapping("/category/list")
    public Result<Page<KnowledgeCategory>> getCategoryList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(adminService.getCategoryList(page, size));
    }

    @PostMapping("/category/save")
    public Result<?> saveCategory(@RequestBody KnowledgeCategory category) {
        adminService.saveCategory(category);
        return Result.success();
    }

    @DeleteMapping("/category/{id}")
    public Result<?> deleteCategory(@PathVariable Long id) {
        adminService.deleteCategory(id);
        return Result.success();
    }

    @GetMapping("/article/list")
    public Result<Page<KnowledgeArticle>> getArticleList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long categoryId) {
        return Result.success(adminService.getArticleList(page, size, categoryId));
    }

    @PostMapping("/article/save")
    public Result<?> saveArticle(@RequestBody KnowledgeArticle article) {
        adminService.saveArticle(article);
        return Result.success();
    }

    @DeleteMapping("/article/{id}")
    public Result<?> deleteArticle(@PathVariable Long id) {
        adminService.deleteArticle(id);
        return Result.success();
    }

    @GetMapping("/question/list")
    public Result<Page<Question>> getQuestionList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long categoryId) {
        return Result.success(adminService.getQuestionList(page, size, categoryId));
    }

    @PostMapping("/question/save")
    public Result<?> saveQuestion(@RequestBody Question question) {
        adminService.saveQuestion(question);
        return Result.success();
    }

    @DeleteMapping("/question/{id}")
    public Result<?> deleteQuestion(@PathVariable Long id) {
        adminService.deleteQuestion(id);
        return Result.success();
    }

    @GetMapping("/news/list")
    public Result<Page<News>> getNewsList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(adminService.getNewsList(page, size));
    }

    @PostMapping("/news/save")
    public Result<?> saveNews(@RequestBody News news) {
        adminService.saveNews(news);
        return Result.success();
    }

    @DeleteMapping("/news/{id}")
    public Result<?> deleteNews(@PathVariable Long id) {
        adminService.deleteNews(id);
        return Result.success();
    }

    @GetMapping("/qa/list")
    public Result<Page<QaPost>> getQaList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(adminService.getQaList(page, size));
    }

    @PutMapping("/qa/status/{id}")
    public Result<?> updateQaStatus(@PathVariable Long id, @RequestParam Integer status) {
        adminService.updateQaStatus(id, status);
        return Result.success();
    }
}
