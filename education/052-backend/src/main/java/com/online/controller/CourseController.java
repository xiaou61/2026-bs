package com.online.controller;

import com.online.common.Result;
import com.online.service.BannerService;
import com.online.service.CategoryService;
import com.online.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BannerService bannerService;

    @GetMapping("/banner/list")
    public Result<?> getBanners() {
        return Result.success(bannerService.getList());
    }

    @GetMapping("/category/list")
    public Result<?> getCategories() {
        return Result.success(categoryService.getList());
    }

    @GetMapping("/course/list")
    public Result<?> getCourseList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String orderBy) {
        return Result.success(courseService.getList(pageNum, pageSize, categoryId, keyword, orderBy));
    }

    @GetMapping("/course/recommend")
    public Result<?> getRecommend() {
        return Result.success(courseService.getRecommend());
    }

    @GetMapping("/course/hot")
    public Result<?> getHot() {
        return Result.success(courseService.getHot());
    }

    @GetMapping("/course/latest")
    public Result<?> getLatest() {
        return Result.success(courseService.getLatest());
    }

    @GetMapping("/course/{id}")
    public Result<?> getCourseDetail(@PathVariable Long id) {
        return Result.success(courseService.getDetail(id));
    }

    @GetMapping("/course/{id}/chapters")
    public Result<?> getChapters(@PathVariable Long id) {
        return Result.success(courseService.getChapters(id));
    }
}
