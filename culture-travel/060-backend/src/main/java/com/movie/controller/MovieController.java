package com.movie.controller;

import com.movie.common.Result;
import com.movie.entity.Movie;
import com.movie.service.MovieService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Resource
    private MovieService movieService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String title,
                             @RequestParam(required = false) Long categoryId) {
        return Result.success(movieService.getPage(pageNum, pageSize, title, categoryId));
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(movieService.getById(id));
    }

    @GetMapping("/hot")
    public Result<?> getHot() {
        return Result.success(movieService.getHot());
    }

    @PostMapping
    public Result<?> add(@RequestBody Movie movie) {
        movieService.add(movie);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Movie movie) {
        movieService.update(movie);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        movieService.delete(id);
        return Result.success();
    }
}
