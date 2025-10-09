package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Course;
import com.xiaou.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public Result<List<Course>> list(@RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) Long teacherId,
                                     @RequestParam(required = false) String courseType) {
        List<Course> courses = courseService.findAll(keyword, teacherId, courseType);
        return Result.success(courses);
    }

    @GetMapping("/{id}")
    public Result<Course> getById(@PathVariable Long id) {
        Course course = courseService.findById(id);
        return Result.success(course);
    }

    @GetMapping("/my")
    public Result<List<Course>> getMyCourses(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<Course> courses = courseService.findByTeacherId(userId);
        return Result.success(courses);
    }

    @PostMapping
    public Result<Course> create(@RequestBody Course course) {
        Course newCourse = courseService.create(course);
        return Result.success(newCourse);
    }

    @PutMapping("/{id}")
    public Result<Course> update(@PathVariable Long id, @RequestBody Course course) {
        course.setId(id);
        Course updatedCourse = courseService.update(course);
        return Result.success(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return Result.success();
    }
}

