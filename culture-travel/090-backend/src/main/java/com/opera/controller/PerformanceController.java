package com.opera.controller;

import com.github.pagehelper.PageInfo;
import com.opera.common.Result;
import com.opera.entity.PerformanceSchedule;
import com.opera.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    @Autowired
    private PerformanceService scheduleService;

    @GetMapping("/list")
    public Result<PageInfo<PerformanceSchedule>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(required = false) String courseName,
                                                 @RequestParam(required = false) Long artistId,
                                                 @RequestParam(required = false) Long termId,
                                                 @RequestParam(required = false) Long classId,
                                                 @RequestParam(required = false) Integer status) {
        return Result.success(scheduleService.list(courseName, artistId, termId, classId, status, pageNum, pageSize));
    }

    @GetMapping("/artist")
    public Result<List<PerformanceSchedule>> artist(@RequestAttribute("userId") Long userId) {
        return Result.success(scheduleService.artistList(userId));
    }

    @GetMapping("/member")
    public Result<List<PerformanceSchedule>> member(@RequestAttribute("userId") Long userId) {
        return Result.success(scheduleService.memberList(userId));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody PerformanceSchedule entity, @RequestAttribute("role") String role) {
        scheduleService.add(entity, role);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody PerformanceSchedule entity, @RequestAttribute("role") String role) {
        scheduleService.update(entity, role);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("role") String role) {
        scheduleService.delete(id, role);
        return Result.success();
    }
}


