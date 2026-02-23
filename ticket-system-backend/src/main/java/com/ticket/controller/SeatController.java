package com.ticket.controller;

import com.ticket.common.Result;
import com.ticket.entity.Seat;
import com.ticket.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/showtime/{showtimeId}")
    public Result<List<Seat>> getSeatsByShowtime(@PathVariable Long showtimeId) {
        return Result.success(seatService.getSeatsByShowtime(showtimeId));
    }

    @PostMapping("/lock")
    public Result<Boolean> lockSeats(@RequestBody List<Long> seatIds, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(seatService.lockSeats(seatIds, userId));
    }
}
