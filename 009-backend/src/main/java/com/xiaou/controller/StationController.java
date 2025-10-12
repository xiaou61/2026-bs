package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.Result;
import com.xiaou.entity.Express;
import com.xiaou.entity.Station;
import com.xiaou.service.ExpressService;
import com.xiaou.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/station")
@CrossOrigin
public class StationController {

    @Autowired
    private StationService stationService;

    @Autowired
    private ExpressService expressService;

    @GetMapping("/list")
    public Result<?> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status) {
        
        Page<Station> pageObj = new Page<>(page, size);
        QueryWrapper<Station> wrapper = new QueryWrapper<>();
        
        if (status != null) {
            wrapper.eq("status", status);
        }
        
        wrapper.orderByAsc("id");
        Page<Station> result = stationService.page(pageObj, wrapper);
        
        return Result.success(result);
    }

    @GetMapping("/all")
    public Result<?> all() {
        QueryWrapper<Station> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        wrapper.orderByAsc("id");
        return Result.success(stationService.list(wrapper));
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        Station station = stationService.getById(id);
        if (station == null) {
            return Result.error("代收点不存在");
        }
        return Result.success(station);
    }

    @PostMapping
    public Result<?> add(@RequestBody Station station) {
        station.setCurrentStock(0);
        station.setStatus(1);
        stationService.save(station);
        return Result.success("添加成功");
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Station station) {
        Station dbStation = stationService.getById(id);
        if (dbStation == null) {
            return Result.error("代收点不存在");
        }
        station.setId(id);
        stationService.updateById(station);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        QueryWrapper<Express> wrapper = new QueryWrapper<>();
        wrapper.eq("station_id", id);
        wrapper.eq("status", 0);
        long count = expressService.count(wrapper);
        
        if (count > 0) {
            return Result.error("该代收点还有待取快递，无法删除");
        }
        
        stationService.removeById(id);
        return Result.success("删除成功");
    }

    @GetMapping("/{id}/stats")
    public Result<?> stats(@PathVariable Long id) {
        Station station = stationService.getById(id);
        if (station == null) {
            return Result.error("代收点不存在");
        }

        QueryWrapper<Express> wrapper = new QueryWrapper<>();
        wrapper.eq("station_id", id);
        wrapper.eq("status", 0);
        long waitingCount = expressService.count(wrapper);

        Map<String, Object> stats = new HashMap<>();
        stats.put("station", station);
        stats.put("waitingCount", waitingCount);
        stats.put("capacity", station.getShelfCapacity());
        stats.put("usage", String.format("%.2f%%", (double) waitingCount / station.getShelfCapacity() * 100));

        return Result.success(stats);
    }
}

