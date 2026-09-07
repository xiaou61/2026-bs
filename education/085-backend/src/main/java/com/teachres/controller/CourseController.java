package com.teachres.controller;

import com.github.pagehelper.PageInfo;
import com.teachres.common.Result;
import com.teachres.entity.MathCourse;
import com.teachres.service.CourseService;
import com.teachres.utils.AuthUtils;
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
                                             @RequestParam(required = false) String term,
                                             @RequestAttribute("userId") Long userId,
                                             @RequestAttribute("role") String role) {
        AuthUtils.requireRole(role, "admin", "teacher");
        Long scopedTeacherId = "teacher".equals(role) ? userId : teacherId;
        return Result.success(courseService.list(courseName, categoryId, scopedTeacherId, status, term, pageNum, pageSize));
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
                              @RequestAttribute("userId") Long userId,
                              @RequestAttribute("role") String role) {
        AuthUtils.requireRole(role, "admin", "teacher");
        courseService.add(course, userId, role);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody MathCourse course,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        AuthUtils.requireRole(role, "admin", "teacher");
        courseService.update(course, userId, role);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        AuthUtils.requireRole(role, "admin", "teacher");
        courseService.delete(id, userId, role);
        return Result.success();
    }
}
