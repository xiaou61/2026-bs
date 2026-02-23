package com.ticket.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.Result;
import com.ticket.entity.Movie;
import com.ticket.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/list")
    public Result<Page<Movie>> listMovies(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category) {
        return Result.success(movieService.listMovies(pageNum, pageSize, title, status, category));
    }

    @GetMapping("/{id}")
    public Result<Movie> getMovieById(@PathVariable Long id) {
        return Result.success(movieService.getMovieById(id));
    }

    @PostMapping("/add")
    public Result<Void> addMovie(@RequestBody Movie movie) {
        movieService.addMovie(movie);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<Void> updateMovie(@RequestBody Movie movie) {
        movieService.updateMovie(movie);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return Result.success();
    }

    @GetMapping("/recommend")
    public Result<List<Movie>> getRecommendMovies() {
        return Result.success(movieService.getRecommendMovies());
    }
}
