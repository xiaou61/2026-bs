package com.movie.controller;

import com.movie.common.Result;
import com.movie.entity.Hall;
import com.movie.service.HallService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/hall")
public class HallController {

    @Resource
    private HallService hallService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) Long cinemaId,
                             @RequestParam(required = false) String name) {
        return Result.success(hallService.getPage(pageNum, pageSize, cinemaId, name));
    }

    @GetMapping("/list")
    public Result<?> getByCinemaId(@RequestParam Long cinemaId) {
        return Result.success(hallService.getByCinemaId(cinemaId));
    }

    @PostMapping
    public Result<?> add(@RequestBody Hall hall) {
        hallService.add(hall);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Hall hall) {
        hallService.update(hall);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        hallService.delete(id);
        return Result.success();
    }
}
