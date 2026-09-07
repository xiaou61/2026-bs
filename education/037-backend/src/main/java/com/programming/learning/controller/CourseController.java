package com.programming.learning.controller;

import com.programming.learning.common.PageResult;
import com.programming.learning.common.Result;
import com.programming.learning.entity.Course;
import com.programming.learning.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 课程控制器
 */
@RestController
@RequestMapping("/courses")
@Tag(name = "课程接口")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/list")
    @Operation(summary = "课程分页列表")
    public Result<PageResult<Course>> listCourses(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String category) {
        return Result.success(courseService.listCourses(pageNum, pageSize, category));
    }

    @GetMapping("/hot")
    @Operation(summary = "热门课程")
    public Result<List<Course>> listHotCourses(@RequestParam(defaultValue = "5") Integer limit) {
        return Result.success(courseService.listHotCourses(limit));
    }

    @GetMapping("/{id}")
    @Operation(summary = "课程详情")
    public Result<Course> getCourse(@PathVariable Long id) {
        return Result.success(courseService.getCourse(id));
    }
}
