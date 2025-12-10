package com.xiaou.bike.controller;

import com.xiaou.bike.common.Result;
import com.xiaou.bike.entity.Bicycle;
import com.xiaou.bike.entity.Station;
import com.xiaou.bike.service.BicycleService;
import com.xiaou.bike.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 停车点控制器
 */
@RestController
@RequestMapping("/station")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;
    private final BicycleService bicycleService;

    /**
     * 获取所有停车点
     */
    @GetMapping("/list")
    public Result<List<Station>> listAll() {
        List<Station> list = stationService.listAll();
        return Result.success(list);
    }

    /**
     * 获取停车点详情
     */
    @GetMapping("/{id}")
    public Result<Station> getById(@PathVariable Long id) {
        Station station = stationService.getById(id);
        return Result.success(station);
    }

    /**
     * 获取停车点可用车辆
     */
    @GetMapping("/{id}/bikes")
    public Result<List<Bicycle>> getAvailableBikes(@PathVariable Long id) {
        List<Bicycle> bikes = bicycleService.getAvailableBikes(id);
        return Result.success(bikes);
    }
}
