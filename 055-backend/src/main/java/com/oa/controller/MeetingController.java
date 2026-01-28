package com.oa.controller;

import com.oa.common.Result;
import com.oa.entity.Meeting;
import com.oa.service.MeetingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meeting")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String date) {
        return Result.success(meetingService.getList(pageNum, pageSize, date));
    }

    @GetMapping("/my")
    public Result my(HttpServletRequest request, String date) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(meetingService.getMyMeetings(userId, date));
    }

    @PostMapping
    public Result add(HttpServletRequest request, @RequestBody Meeting meeting) {
        Long userId = (Long) request.getAttribute("userId");
        meetingService.add(userId, meeting);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Meeting meeting) {
        meetingService.update(meeting);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        meetingService.delete(id);
        return Result.success();
    }
}
