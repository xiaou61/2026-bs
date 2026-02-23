package com.ticket.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.Result;
import com.ticket.entity.Hall;
import com.ticket.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hall")
public class HallController {

    @Autowired
    private HallService hallService;

    @GetMapping("/list")
    public Result<Page<Hall>> listHalls(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long cinemaId) {
        return Result.success(hallService.listHalls(pageNum, pageSize, cinemaId));
    }

    @GetMapping("/{id}")
    public Result<Hall> getHallById(@PathVariable Long id) {
        return Result.success(hallService.getHallById(id));
    }

    @PostMapping("/add")
    public Result<Void> addHall(@RequestBody Hall hall) {
        hallService.addHall(hall);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<Void> updateHall(@RequestBody Hall hall) {
        hallService.updateHall(hall);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteHall(@PathVariable Long id) {
        hallService.deleteHall(id);
        return Result.success();
    }
}
