package com.bike.controller;

import com.bike.common.Result;
import com.bike.entity.Bike;
import com.bike.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/bike")
public class BikeController {

    @Autowired
    private BikeService bikeService;

    @GetMapping("/list")
    public Result<?> getList(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String bikeNo,
                             @RequestParam(required = false) Integer type,
                             @RequestParam(required = false) Integer status,
                             @RequestParam(required = false) Long stationId) {
        return Result.success(bikeService.getList(pageNum, pageSize, bikeNo, type, status, stationId));
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(bikeService.getById(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody Bike bike) {
        bikeService.add(bike);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Bike bike) {
        bike.setId(id);
        bikeService.update(bike);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        bikeService.delete(id);
        return Result.success();
    }

    @GetMapping("/available/{stationId}")
    public Result<?> getAvailable(@PathVariable Long stationId) {
        return Result.success(bikeService.getAvailableByStation(stationId));
    }

    @PutMapping("/dispatch")
    public Result<?> dispatch(@RequestBody Map<String, Long> params) {
        bikeService.dispatch(params.get("bikeId"), params.get("targetStationId"));
        return Result.success();
    }
}
