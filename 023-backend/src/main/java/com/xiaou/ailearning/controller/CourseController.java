package com.xiaou.ailearning.controller;

import com.xiaou.ailearning.common.PageResult;
import com.xiaou.ailearning.common.Result;
import com.xiaou.ailearning.entity.Course;
import com.xiaou.ailearning.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/list")
    public Result<PageResult<Course>> getCourseList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        try {
            PageResult<Course> result = courseService.getCourseList(page, size, keyword);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取课程列表失败: " + e.getMessage());
        }
    }

    @GetMapping("/{courseId}")
    public Result<Course> getCourseDetail(@PathVariable Long courseId) {
        try {
            Course course = courseService.getCourseDetail(courseId);
            if (course != null) {
                return Result.success(course);
            } else {
                return Result.error("课程不存在");
            }
        } catch (Exception e) {
            return Result.error("获取课程详情失败: " + e.getMessage());
        }
    }

    @GetMapping("/recommended")
    public Result<List<Course>> getRecommendedCourses(@RequestAttribute Long userId, @RequestParam(defaultValue = "10") int limit) {
        try {
            List<Course> courses = courseService.getRecommendedCourses(userId, limit);
            return Result.success(courses);
        } catch (Exception e) {
            return Result.error("获取推荐课程失败: " + e.getMessage());
        }
    }

    @GetMapping("/category/{categoryId}")
    public Result<List<Course>> getCoursesByCategory(@PathVariable Long categoryId) {
        try {
            List<Course> courses = courseService.getCoursesByCategory(categoryId);
            return Result.success(courses);
        } catch (Exception e) {
            return Result.error("获取分类课程失败: " + e.getMessage());
        }
    }

    @GetMapping("/hot")
    public Result<List<Course>> getHotCourses(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<Course> courses = courseService.getHotCourses(limit);
            return Result.success(courses);
        } catch (Exception e) {
            return Result.error("获取热门课程失败: " + e.getMessage());
        }
    }

    @PostMapping("/enroll")
    public Result<Void> enrollCourse(@RequestAttribute Long userId, @RequestBody Map<String, Long> request) {
        try {
            Long courseId = request.get("courseId");
            if (courseId == null) {
                return Result.error("课程ID不能为空");
            }
            
            boolean success = courseService.enrollCourse(userId, courseId);
            if (success) {
                return Result.success("报名成功");
            } else {
                return Result.error("报名失败");
            }
        } catch (Exception e) {
            return Result.error("报名失败: " + e.getMessage());
        }
    }

    @GetMapping("/enrollment-status/{courseId}")
    public Result<Map<String, Boolean>> checkEnrollmentStatus(@RequestAttribute Long userId, @PathVariable Long courseId) {
        try {
            boolean isEnrolled = courseService.isEnrolled(userId, courseId);
            return Result.success(Map.of("isEnrolled", isEnrolled));
        } catch (Exception e) {
            return Result.error("检查报名状态失败: " + e.getMessage());
        }
    }

    @GetMapping("/my-courses")
    public Result<List<Course>> getUserEnrolledCourses(@RequestAttribute Long userId) {
        try {
            List<Course> courses = courseService.getUserEnrolledCourses(userId);
            return Result.success(courses);
        } catch (Exception e) {
            return Result.error("获取我的课程失败: " + e.getMessage());
        }
    }
}