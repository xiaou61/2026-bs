package com.bike.controller;

import com.bike.common.Result;
import com.bike.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/ride")
public class RideController {

    @Autowired
    private RideService rideService;

    @PostMapping("/start")
    public Result<?> startRide(HttpServletRequest request, @RequestBody Map<String, Long> params) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(rideService.startRide(userId, params.get("bikeId"), params.get("stationId")));
    }

    @PostMapping("/end")
    public Result<?> endRide(HttpServletRequest request, @RequestBody Map<String, Long> params) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(rideService.endRide(userId, params.get("endStationId")));
    }

    @GetMapping("/current")
    public Result<?> getCurrent(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(rideService.getCurrent(userId));
    }

    @GetMapping("/list")
    public Result<?> getMyList(HttpServletRequest request,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(rideService.getMyList(userId, pageNum, pageSize));
    }

    @GetMapping("/all")
    public Result<?> getAllList(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(required = false) String orderNo,
                               @RequestParam(required = false) Long userId,
                               @RequestParam(required = false) Integer status) {
        return Result.success(rideService.getAllList(pageNum, pageSize, orderNo, userId, status));
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(rideService.getById(id));
    }
}
