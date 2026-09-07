package com.movie.controller;

import com.movie.common.Result;
import com.movie.entity.MovieCategory;
import com.movie.service.MovieCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/movieCategory")
public class MovieCategoryController {

    @Resource
    private MovieCategoryService movieCategoryService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String name) {
        return Result.success(movieCategoryService.getPage(pageNum, pageSize, name));
    }

    @GetMapping("/list")
    public Result<?> getAll() {
        return Result.success(movieCategoryService.getAll());
    }

    @PostMapping
    public Result<?> add(@RequestBody MovieCategory category) {
        movieCategoryService.add(category);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody MovieCategory category) {
        movieCategoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        movieCategoryService.delete(id);
        return Result.success();
    }
}
