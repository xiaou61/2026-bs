package com.xiaou.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.Result;
import com.xiaou.dto.CreateTaskDTO;
import com.xiaou.dto.SignDTO;
import com.xiaou.entity.AttendanceRecord;
import com.xiaou.entity.AttendanceStat;
import com.xiaou.entity.AttendanceTask;
import com.xiaou.service.AttendanceRecordService;
import com.xiaou.service.AttendanceStatService;
import com.xiaou.service.AttendanceTaskService;
import com.xiaou.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceTaskService taskService;

    @Autowired
    private AttendanceRecordService recordService;

    @Autowired
    private AttendanceStatService statService;

    @Autowired
    private JwtUtil jwtUtil;

    // ============ 签到任务相关 ============

    @PostMapping("/task/create")
    public Result<?> createTask(@RequestHeader("Authorization") String token,
                                @RequestBody CreateTaskDTO dto) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        AttendanceTask task = taskService.createTask(userId, dto);
        return Result.success(task);
    }

    @PutMapping("/task/end/{taskId}")
    public Result<?> endTask(@RequestHeader("Authorization") String token,
                             @PathVariable Long taskId) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        taskService.endTask(taskId, userId);
        return Result.success("签到已结束");
    }

    @GetMapping("/task/page")
    public Result<?> pageTasksByCourse(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       @RequestParam Long courseId) {
        IPage<AttendanceTask> result = taskService.pageTasksByCourse(page, size, courseId);
        return Result.success(result);
    }

    @GetMapping("/task/active")
    public Result<?> getActiveTasks(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        List<AttendanceTask> list = taskService.getActiveTasksForStudent(userId);
        return Result.success(list);
    }

    @GetMapping("/task/detail/{taskId}")
    public Result<?> getTaskDetail(@PathVariable Long taskId) {
        Map<String, Object> detail = taskService.getTaskDetail(taskId);
        return Result.success(detail);
    }

    @PutMapping("/task/refreshQr/{taskId}")
    public Result<?> refreshQrCode(@RequestHeader("Authorization") String token,
                                   @PathVariable Long taskId) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        String newQrCode = taskService.refreshQrCode(taskId, userId);
        if (newQrCode != null) {
            return Result.success(newQrCode);
        }
        return Result.error("刷新失败");
    }

    @GetMapping("/task/{taskId}")
    public Result<?> getTaskById(@PathVariable Long taskId) {
        return Result.success(taskService.getById(taskId));
    }

    // ============ 签到记录相关 ============

    @PostMapping("/sign")
    public Result<?> sign(@RequestHeader("Authorization") String token,
                          @RequestBody SignDTO dto) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        try {
            AttendanceRecord record = recordService.sign(userId, dto);
            return Result.success(record);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/record/page")
    public Result<?> pageRecordsByTask(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       @RequestParam Long taskId) {
        IPage<AttendanceRecord> result = recordService.pageRecordsByTask(page, size, taskId);
        return Result.success(result);
    }

    @GetMapping("/record/student")
    public Result<?> getStudentRecords(@RequestHeader("Authorization") String token,
                                       @RequestParam Long courseId) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        List<AttendanceRecord> list = recordService.getStudentRecords(userId, courseId);
        return Result.success(list);
    }

    @GetMapping("/record/stats/{taskId}")
    public Result<?> getTaskSignStats(@PathVariable Long taskId) {
        Map<String, Object> stats = recordService.getTaskSignStats(taskId);
        return Result.success(stats);
    }

    // ============ 考勤统计相关 ============

    @GetMapping("/stat/student")
    public Result<?> getStudentCourseStat(@RequestHeader("Authorization") String token,
                                          @RequestParam Long courseId) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        AttendanceStat stat = statService.getStudentCourseStat(userId, courseId);
        return Result.success(stat);
    }

    @GetMapping("/stat/student/all")
    public Result<?> getStudentAllStats(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.getUserIdFromToken(token.replace("Bearer ", ""));
        List<AttendanceStat> list = statService.getStudentAllStats(userId);
        return Result.success(list);
    }

    @GetMapping("/stat/course/{courseId}")
    public Result<?> getCourseAllStats(@PathVariable Long courseId) {
        List<AttendanceStat> list = statService.getCourseAllStats(courseId);
        return Result.success(list);
    }

    @GetMapping("/stat/course/summary/{courseId}")
    public Result<?> getCourseStatSummary(@PathVariable Long courseId) {
        Map<String, Object> summary = statService.getCourseStatSummary(courseId);
        return Result.success(summary);
    }
}
