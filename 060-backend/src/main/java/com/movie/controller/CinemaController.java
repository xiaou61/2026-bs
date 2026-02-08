package com.movie.controller;

import com.movie.common.Result;
import com.movie.entity.Cinema;
import com.movie.service.CinemaService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/cinema")
public class CinemaController {

    @Resource
    private CinemaService cinemaService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String name) {
        return Result.success(cinemaService.getPage(pageNum, pageSize, name));
    }

    @GetMapping("/list")
    public Result<?> getAll() {
        return Result.success(cinemaService.getAll());
    }

    @PostMapping
    public Result<?> add(@RequestBody Cinema cinema) {
        cinemaService.add(cinema);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Cinema cinema) {
        cinemaService.update(cinema);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        cinemaService.delete(id);
        return Result.success();
    }
}
