package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Grade;
import com.xiaou.service.GradeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Data
    public static class SubmitGradeRequest {
        @NotNull(message = "学生ID不能为空")
        private Long studentId;
        @NotNull(message = "课程ID不能为空")
        private Long courseId;
        @NotNull(message = "成绩不能为空")
        private BigDecimal score;
    }

    @GetMapping("/my")
    public Result<List<Grade>> getMyGrades(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        
        List<Grade> grades;
        if ("student".equals(role)) {
            grades = gradeService.findByStudentId(userId);
        } else if ("teacher".equals(role)) {
            grades = gradeService.findByTeacherId(userId);
        } else {
            return Result.error("权限不足");
        }
        
        return Result.success(grades);
    }

    @GetMapping("/student/{studentId}")
    public Result<List<Grade>> getGradesByStudent(@PathVariable Long studentId) {
        List<Grade> grades = gradeService.findByStudentId(studentId);
        return Result.success(grades);
    }

    @GetMapping("/course/{courseId}")
    public Result<List<Grade>> getGradesByCourse(@PathVariable Long courseId) {
        List<Grade> grades = gradeService.findByCourseId(courseId);
        return Result.success(grades);
    }

    @PostMapping("/submit")
    public Result<Grade> submitGrade(@Valid @RequestBody SubmitGradeRequest request, HttpServletRequest httpRequest) {
        Long teacherId = (Long) httpRequest.getAttribute("userId");
        Grade grade = gradeService.submitGrade(request.getStudentId(), request.getCourseId(), request.getScore(), teacherId);
        return Result.success(grade);
    }
}

