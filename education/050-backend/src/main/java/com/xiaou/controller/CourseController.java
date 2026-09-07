package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.entity.Course;
import com.xiaou.entity.CourseSchedule;
import com.xiaou.entity.User;
import com.xiaou.service.CourseScheduleService;
import com.xiaou.service.CourseService;
import com.xiaou.service.CourseStudentService;
import com.xiaou.service.UserService;
import com.xiaou.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseScheduleService courseScheduleService;

    @Autowired
    private CourseStudentService courseStudentService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/page")
    public Result<?> pageCourses(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size,
                                 @RequestParam(required = false) Long semesterId,
                                 @RequestParam(required = false) Long teacherId,
                                 @RequestParam(required = false) String keyword) {
        IPage<Course> result = courseService.pageCourses(page, size, semesterId, teacherId, keyword);
        return Result.success(result);
    }

    @GetMapping("/teacher")
    public Result<?> getTeacherCourses(@RequestHeader("Authorization") String token,
                                       @RequestParam(required = false) Long semesterId) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        List<Course> list = courseService.getTeacherCourses(userId, semesterId);
        return Result.success(list);
    }

    @GetMapping("/student")
    public Result<?> getStudentCourses(@RequestHeader("Authorization") String token,
                                       @RequestParam(required = false) Long semesterId) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        List<Course> list = courseService.getStudentCourses(userId, semesterId);
        return Result.success(list);
    }

    @GetMapping("/detail/{id}")
    public Result<?> getCourseDetail(@PathVariable Long id) {
        Map<String, Object> detail = courseService.getCourseDetail(id);
        return Result.success(detail);
    }

    @PostMapping
    public Result<?> addCourse(@RequestBody Course course) {
        course.setCreateTime(LocalDateTime.now());
        courseService.save(course);
        return Result.success("添加成功");
    }

    @PutMapping("/{id}")
    public Result<?> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        course.setId(id);
        course.setUpdateTime(LocalDateTime.now());
        courseService.updateById(course);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteCourse(@PathVariable Long id) {
        courseService.removeById(id);
        return Result.success("删除成功");
    }

    @GetMapping("/{id}")
    public Result<?> getCourseById(@PathVariable Long id) {
        return Result.success(courseService.getById(id));
    }

    // 课程安排相关
    @GetMapping("/schedule/{courseId}")
    public Result<?> getCourseSchedules(@PathVariable Long courseId) {
        List<CourseSchedule> list = courseScheduleService.getCourseSchedules(courseId);
        return Result.success(list);
    }

    @GetMapping("/schedule/today")
    public Result<?> getTodaySchedules(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        User user = userService.getById(userId);
        List<CourseSchedule> list = courseScheduleService.getTodaySchedules(userId, user.getRole());
        return Result.success(list);
    }

    @PostMapping("/schedule")
    public Result<?> addSchedule(@RequestBody CourseSchedule schedule) {
        schedule.setCreateTime(LocalDateTime.now());
        courseScheduleService.save(schedule);
        return Result.success("添加成功");
    }

    @PutMapping("/schedule/{id}")
    public Result<?> updateSchedule(@PathVariable Long id, @RequestBody CourseSchedule schedule) {
        schedule.setId(id);
        courseScheduleService.updateById(schedule);
        return Result.success("更新成功");
    }

    @DeleteMapping("/schedule/{id}")
    public Result<?> deleteSchedule(@PathVariable Long id) {
        courseScheduleService.removeById(id);
        return Result.success("删除成功");
    }

    // 选课相关
    @GetMapping("/students/{courseId}")
    public Result<?> getCourseStudents(@PathVariable Long courseId) {
        List<User> students = courseStudentService.getCourseStudents(courseId);
        return Result.success(students);
    }

    @PostMapping("/select/{courseId}")
    public Result<?> selectCourse(@RequestHeader("Authorization") String token,
                                  @PathVariable Long courseId) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        boolean success = courseStudentService.selectCourse(userId, courseId);
        if (success) {
            return Result.success("选课成功");
        }
        return Result.error("您已选择该课程");
    }

    @DeleteMapping("/drop/{courseId}")
    public Result<?> dropCourse(@RequestHeader("Authorization") String token,
                                @PathVariable Long courseId) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        boolean success = courseStudentService.dropCourse(userId, courseId);
        if (success) {
            return Result.success("退课成功");
        }
        return Result.error("退课失败");
    }
}
