package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.CourseSelection;
import com.xiaou.service.CourseSelectionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/selections")
public class CourseSelectionController {

    @Autowired
    private CourseSelectionService courseSelectionService;

    @GetMapping("/my")
    public Result<List<CourseSelection>> getMySelections(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<CourseSelection> selections = courseSelectionService.findByStudentId(userId);
        return Result.success(selections);
    }

    @GetMapping("/course/{courseId}")
    public Result<List<CourseSelection>> getSelectionsByCourse(@PathVariable Long courseId) {
        List<CourseSelection> selections = courseSelectionService.findByCourseId(courseId);
        return Result.success(selections);
    }

    @PostMapping("/select/{courseId}")
    public Result<CourseSelection> selectCourse(@PathVariable Long courseId, HttpServletRequest request) {
        Long studentId = (Long) request.getAttribute("userId");
        CourseSelection selection = courseSelectionService.selectCourse(studentId, courseId);
        return Result.success(selection);
    }

    @DeleteMapping("/drop/{courseId}")
    public Result<Void> dropCourse(@PathVariable Long courseId, HttpServletRequest request) {
        Long studentId = (Long) request.getAttribute("userId");
        courseSelectionService.dropCourse(studentId, courseId);
        return Result.success();
    }
}

