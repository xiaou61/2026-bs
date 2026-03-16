package com.teachres.controller;

import com.github.pagehelper.PageInfo;
import com.teachres.common.Result;
import com.teachres.entity.MathCourse;
import com.teachres.service.CourseService;
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
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/list")
    public Result<PageInfo<MathCourse>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(required = false) String courseName,
                                             @RequestParam(required = false) Long categoryId,
                                             @RequestParam(required = false) Long teacherId,
                                             @RequestParam(required = false) Integer status,
                                             @RequestParam(required = false) String term) {
        return Result.success(courseService.list(courseName, categoryId, teacherId, status, term, pageNum, pageSize));
    }

    @GetMapping("/enabled")
    public Result<List<MathCourse>> enabled() {
        return Result.success(courseService.enabledList());
    }

    @GetMapping("/detail/{id}")
    public Result<MathCourse> detail(@PathVariable Long id) {
        return Result.success(courseService.detail(id));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody MathCourse course,
                              @RequestAttribute("userId") Long userId) {
        courseService.add(course, userId);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody MathCourse course) {
        courseService.update(course);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        courseService.delete(id);
        return Result.success();
    }
}
