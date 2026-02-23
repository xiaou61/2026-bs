package com.ticket.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.Result;
import com.ticket.entity.Showtime;
import com.ticket.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/showtime")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;

    @GetMapping("/list")
    public Result<Page<Showtime>> listShowtimes(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long movieId,
            @RequestParam(required = false) Long cinemaId,
            @RequestParam(required = false) String date) {
        return Result.success(showtimeService.listShowtimes(pageNum, pageSize, movieId, cinemaId, date));
    }

    @GetMapping("/{id}")
    public Result<Showtime> getShowtimeById(@PathVariable Long id) {
        return Result.success(showtimeService.getShowtimeById(id));
    }

    @PostMapping("/add")
    public Result<Void> addShowtime(@RequestBody Showtime showtime) {
        showtimeService.addShowtime(showtime);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<Void> updateShowtime(@RequestBody Showtime showtime) {
        showtimeService.updateShowtime(showtime);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return Result.success();
    }
}
