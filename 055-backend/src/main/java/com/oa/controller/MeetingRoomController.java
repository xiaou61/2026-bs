package com.oa.controller;

import com.oa.common.Result;
import com.oa.entity.MeetingRoom;
import com.oa.service.MeetingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meetingRoom")
@RequiredArgsConstructor
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(meetingRoomService.getList(pageNum, pageSize));
    }

    @GetMapping("/all")
    public Result all() {
        return Result.success(meetingRoomService.getAll());
    }

    @PostMapping
    public Result add(@RequestBody MeetingRoom room) {
        meetingRoomService.add(room);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody MeetingRoom room) {
        meetingRoomService.update(room);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        meetingRoomService.delete(id);
        return Result.success();
    }
}
