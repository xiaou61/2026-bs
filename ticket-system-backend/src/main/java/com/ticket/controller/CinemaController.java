package com.ticket.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.Result;
import com.ticket.entity.Cinema;
import com.ticket.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cinema")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/list")
    public Result<Page<Cinema>> listCinemas(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name) {
        return Result.success(cinemaService.listCinemas(pageNum, pageSize, name));
    }

    @GetMapping("/{id}")
    public Result<Cinema> getCinemaById(@PathVariable Long id) {
        return Result.success(cinemaService.getCinemaById(id));
    }

    @PostMapping("/add")
    public Result<Void> addCinema(@RequestBody Cinema cinema) {
        cinemaService.addCinema(cinema);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<Void> updateCinema(@RequestBody Cinema cinema) {
        cinemaService.updateCinema(cinema);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteCinema(@PathVariable Long id) {
        cinemaService.deleteCinema(id);
        return Result.success();
    }
}
