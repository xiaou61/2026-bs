package com.course.controller;

import com.github.pagehelper.PageInfo;
import com.course.common.Result;
import com.course.entity.CourseSchedule;
import com.course.service.ScheduleService;
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
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/list")
    public Result<PageInfo<CourseSchedule>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(required = false) String courseName,
                                                 @RequestParam(required = false) Long teacherId,
                                                 @RequestParam(required = false) Long termId,
                                                 @RequestParam(required = false) Long classId,
                                                 @RequestParam(required = false) Integer status) {
        return Result.success(scheduleService.list(courseName, teacherId, termId, classId, status, pageNum, pageSize));
    }

    @GetMapping("/teacher")
    public Result<List<CourseSchedule>> teacher(@RequestAttribute("userId") Long userId) {
        return Result.success(scheduleService.teacherList(userId));
    }

    @GetMapping("/student")
    public Result<List<CourseSchedule>> student(@RequestAttribute("userId") Long userId) {
        return Result.success(scheduleService.studentList(userId));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody CourseSchedule entity, @RequestAttribute("role") String role) {
        scheduleService.add(entity, role);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody CourseSchedule entity, @RequestAttribute("role") String role) {
        scheduleService.update(entity, role);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("role") String role) {
        scheduleService.delete(id, role);
        return Result.success();
    }
}
