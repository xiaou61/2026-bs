package com.bike.controller;

import com.bike.common.Result;
import com.bike.entity.Station;
import com.bike.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/station")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/list")
    public Result<?> getList(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) Integer status) {
        return Result.success(stationService.getList(pageNum, pageSize, name, status));
    }

    @GetMapping("/all")
    public Result<?> getAll() {
        return Result.success(stationService.getAll());
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(stationService.getById(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody Station station) {
        stationService.add(station);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Station station) {
        station.setId(id);
        stationService.update(station);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        stationService.delete(id);
        return Result.success();
    }
}
