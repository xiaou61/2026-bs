package com.ticket.controller;

import com.ticket.common.Result;
import com.ticket.entity.Showtime;
import com.ticket.service.ShowtimeService;
import com.ticket.utils.AuthUtils;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.time.LocalDate;

@RestController
@RequestMapping("/api/showtime")
public class ShowtimeController {

    @Resource
    private ShowtimeService showtimeService;

    @GetMapping("/public/list")
    public Result<?> publicList(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(required = false) Long movieId,
                                @RequestParam(required = false) Long cinemaId,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return Result.success(showtimeService.publicPage(pageNum, pageSize, movieId, cinemaId, date));
    }

    @GetMapping("/public/{id}")
    public Result<?> publicDetail(@PathVariable Long id) {
        return Result.success(showtimeService.getById(id));
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long movieId,
                          @RequestParam(required = false) Long cinemaId,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(showtimeService.page(pageNum, pageSize, movieId, cinemaId, status, date));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Showtime showtime, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        showtimeService.save(showtime);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        showtimeService.deleteById(id);
        return Result.success();
    }
}
