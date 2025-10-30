package com.xiaou.sport.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.sport.common.Result;
import com.xiaou.sport.entity.SportTrack;
import com.xiaou.sport.service.SportTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sport")
public class SportTrackController {

    @Autowired
    private SportTrackService sportTrackService;

    @PostMapping("/track")
    public Result<Void> saveTrack(@RequestBody List<SportTrack> tracks) {
        sportTrackService.saveBatch(tracks);
        return Result.success();
    }

    @GetMapping("/track/{recordId}")
    public Result<List<SportTrack>> getTrack(@PathVariable Long recordId) {
        LambdaQueryWrapper<SportTrack> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SportTrack::getRecordId, recordId)
                .orderByAsc(SportTrack::getTrackTime);
        List<SportTrack> tracks = sportTrackService.list(wrapper);
        return Result.success(tracks);
    }
}
