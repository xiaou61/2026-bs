package com.gongkao.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gongkao.common.Result;
import com.gongkao.entity.Course;
import com.gongkao.service.CourseService;
import com.gongkao.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/list")
    public Result<Page<Course>> list(@RequestParam(defaultValue = "1") int pageNum,
                                     @RequestParam(defaultValue = "10") int pageSize,
                                     @RequestParam(required = false) String title,
                                     @RequestParam(required = false) Long subjectId,
                                     @RequestParam(required = false) Integer status,
                                     HttpServletRequest request) {
        AuthUtils.requireAdminOrTeacher(request);
        Long teacherId = AuthUtils.isTeacher(request) ? AuthUtils.getUserId(request) : null;
        return Result.success(courseService.getList(pageNum, pageSize, title, subjectId, status, teacherId));
    }

    @GetMapping("/public/list")
    public Result<List<Course>> publicList(@RequestParam(required = false) Long subjectId) {
        return Result.success(courseService.getPublicList(subjectId));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Course course, HttpServletRequest request) {
        AuthUtils.requireAdminOrTeacher(request);
        if (AuthUtils.isTeacher(request)) {
            course.setTeacherId(AuthUtils.getUserId(request));
        }
        courseService.add(course);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Course course, HttpServletRequest request) {
        AuthUtils.requireAdminOrTeacher(request);
        if (AuthUtils.isTeacher(request)) {
            Course current = courseService.getById(course.getId());
            if (!AuthUtils.getUserId(request).equals(current.getTeacherId())) {
                throw new com.gongkao.common.BusinessException(403, "无权操作他人课程");
            }
            course.setTeacherId(current.getTeacherId());
        }
        courseService.update(course);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdminOrTeacher(request);
        if (AuthUtils.isTeacher(request)) {
            Course current = courseService.getById(id);
            if (!AuthUtils.getUserId(request).equals(current.getTeacherId())) {
                throw new com.gongkao.common.BusinessException(403, "无权操作他人课程");
            }
        }
        courseService.delete(id);
        return Result.success();
    }
}
