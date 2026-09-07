package com.ticket.controller;

import com.ticket.common.Result;
import com.ticket.entity.Movie;
import com.ticket.service.MovieService;
import com.ticket.utils.AuthUtils;
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
@RequestMapping("/api/movie")
public class MovieController {

    @Resource
    private MovieService movieService;

    @GetMapping("/public/list")
    public Result<?> publicList(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(required = false) String title,
                                @RequestParam(required = false) String category,
                                @RequestParam(required = false) String type) {
        return Result.success(movieService.publicPage(pageNum, pageSize, title, category, type));
    }

    @GetMapping("/public/recommend")
    public Result<?> recommend(@RequestParam(defaultValue = "8") Integer limit) {
        return Result.success(movieService.recommend(limit));
    }

    @GetMapping("/public/{id}")
    public Result<?> publicDetail(@PathVariable Long id) {
        return Result.success(movieService.getById(id));
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String title,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) String category,
                          @RequestParam(required = false) String type,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(movieService.page(pageNum, pageSize, title, status, category, type));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Movie movie, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        movieService.save(movie);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        movieService.deleteById(id);
        return Result.success();
    }
}
