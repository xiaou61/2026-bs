package com.opera.controller;

import com.github.pagehelper.PageInfo;
import com.opera.common.Result;
import com.opera.entity.CheckinRecord;
import com.opera.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkin")
public class CheckinController {

    @Autowired
    private CheckinService attendanceService;

    @GetMapping("/list")
    public Result<PageInfo<CheckinRecord>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam(required = false) Long scheduleId,
                                                   @RequestParam(required = false) Long memberId,
                                                   @RequestAttribute("userId") Long userId,
                                                   @RequestAttribute("role") String role) {
        return Result.success(attendanceService.list(scheduleId, memberId, userId, role, pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody CheckinRecord entity,
                              @RequestAttribute("userId") Long userId,
                              @RequestAttribute("role") String role) {
        attendanceService.add(entity, userId, role);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody CheckinRecord entity, @RequestAttribute("role") String role) {
        attendanceService.update(entity, role);
        return Result.success();
    }
}


