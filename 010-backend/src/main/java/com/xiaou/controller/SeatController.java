package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Area;
import com.xiaou.entity.Floor;
import com.xiaou.entity.Seat;
import com.xiaou.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/floor/list")
    public Result<List<Floor>> listFloors() {
        List<Floor> floors = seatService.listFloors();
        return Result.success(floors);
    }

    @GetMapping("/area/list")
    public Result<List<Area>> listAreas(@RequestParam(required = false) Long floorId) {
        List<Area> areas = seatService.listAreas(floorId);
        return Result.success(areas);
    }

    @GetMapping("/list")
    public Result<List<Seat>> listSeats(@RequestParam(required = false) Long areaId) {
        List<Seat> seats = seatService.listSeats(areaId);
        return Result.success(seats);
    }

    @GetMapping("/{id}")
    public Result<Seat> getById(@PathVariable Long id) {
        Seat seat = seatService.getById(id);
        return Result.success(seat);
    }
}

