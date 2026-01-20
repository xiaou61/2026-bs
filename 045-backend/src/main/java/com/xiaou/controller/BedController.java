package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.Bed;
import com.xiaou.entity.Building;
import com.xiaou.entity.Room;
import com.xiaou.mapper.BuildingMapper;
import com.xiaou.mapper.RoomMapper;
import com.xiaou.service.BedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "床位管理")
@RestController
@RequestMapping("/api/bed")
@RequiredArgsConstructor
public class BedController {

    private final BedService bedService;
    private final BuildingMapper buildingMapper;
    private final RoomMapper roomMapper;

    @Operation(summary = "可用床位列表")
    @GetMapping("/available")
    public Result<List<Bed>> available() {
        return Result.success(bedService.getAvailableBeds());
    }

    @Operation(summary = "床位统计")
    @GetMapping("/statistics")
    public Result<List<Map<String, Object>>> statistics() {
        return Result.success(bedService.getBedStatistics());
    }

    @Operation(summary = "楼栋列表")
    @GetMapping("/building/list")
    public Result<List<Building>> buildingList() {
        return Result.success(buildingMapper.selectList(new LambdaQueryWrapper<Building>().eq(Building::getStatus, 1)));
    }

    @Operation(summary = "房间列表")
    @GetMapping("/room/list")
    public Result<List<Room>> roomList(@RequestParam(required = false) Long buildingId) {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        if (buildingId != null) {
            wrapper.eq(Room::getBuildingId, buildingId);
        }
        wrapper.eq(Room::getStatus, 1);
        return Result.success(roomMapper.selectList(wrapper));
    }

    @Operation(summary = "房间床位列表")
    @GetMapping("/room/{roomId}/beds")
    public Result<List<Bed>> bedsByRoom(@PathVariable Long roomId) {
        return Result.success(bedService.list(new LambdaQueryWrapper<Bed>().eq(Bed::getRoomId, roomId)));
    }
}
