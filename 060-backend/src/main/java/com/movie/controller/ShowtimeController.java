package com.movie.controller;

import com.movie.common.Result;
import com.movie.entity.Showtime;
import com.movie.service.ShowtimeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/showtime")
public class ShowtimeController {

    @Resource
    private ShowtimeService showtimeService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) Long movieId,
                             @RequestParam(required = false) Long cinemaId,
                             @RequestParam(required = false) String showDate) {
        return Result.success(showtimeService.getPage(pageNum, pageSize, movieId, cinemaId, showDate));
    }

    @GetMapping("/movie/{movieId}")
    public Result<?> getByMovieId(@PathVariable Long movieId) {
        return Result.success(showtimeService.getByMovieId(movieId));
    }

    @PostMapping
    public Result<?> add(@RequestBody Showtime showtime) {
        showtimeService.add(showtime);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Showtime showtime) {
        showtimeService.update(showtime);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        showtimeService.delete(id);
        return Result.success();
    }
}
