package com.teacher.new.service;

import com.teacher.new.entity.TeacherProfile;
import com.teacher.new.entity.User;
import com.teacher.new.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Resource
    private UserService userService;

    @Resource
    private SubjectService subjectService;

    @Resource
    private TeachingClassService teachingClassService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private TaskService taskService;

    @Resource
    private RecordService recordService;

    @Resource
    private AppealService appealService;

    @Resource
    private NoticeService noticeService;

    @Resource
    private UserMapper userMapper;

    public Map<String, Object> stats(Long userId, String role) {
        Map<String, Object> data = new HashMap<>();
        if ("ADMIN".equals(role)) {
            data.put("userCount", userService.countAll());
            data.put("teacherUserCount", userService.countByRole("TEACHER"));
            data.put("studentUserCount", userService.countByRole("STUDENT"));
            data.put("subjectCount", subjectService.countAll());
            data.put("classCount", teachingClassService.countAll());
            data.put("teacherCount", teacherService.countAll());
            data.put("taskCount", taskService.countAll());
            data.put("openTaskCount", taskService.countOpen());
            data.put("recordCount", recordService.countAll());
            data.put("pendingAppealCount", appealService.countPending());
            data.put("noticeCount", noticeService.countAll());
            return data;
        }

        if ("TEACHER".equals(role)) {
            TeacherProfile teacher = teacherService.getByUserId(userId);
            Long teacherId = teacher == null ? null : teacher.getId();
            data.put("myTaskCount", taskService.countByTeacherId(teacherId));
            data.put("myRecordCount", recordService.countByTeacher(teacherId));
            data.put("myAverageScore", recordService.avgScoreByTeacher(teacherId));
            data.put("myAppealCount", appealService.countByTeacherId(teacherId));
            data.put("myPendingAppealCount", appealService.countByTeacherIdAndStatus(teacherId, "WAITING"));
            data.put("noticeCount", noticeService.countActive());
            return data;
        }

        User user = userMapper.selectById(userId);
        Long classId = user == null ? null : user.getClassId();
        long myTaskCount = taskService.countByClassId(classId);
        long myRecordCount = recordService.countByEvaluator(userId);
        data.put("myTaskCount", myTaskCount);
        data.put("myRecordCount", myRecordCount);
        data.put("unfinishedTaskCount", Math.max(myTaskCount - myRecordCount, 0));
        data.put("openTaskCount", taskService.countByClassIdAndStatus(classId, TaskService.STATUS_OPEN));
        data.put("noticeCount", noticeService.countActive());
        return data;
    }

    public Map<String, Object> trend(Long userId, String role) {
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> daily = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        for (int i = 6; i >= 0; i--) {
            LocalDate day = LocalDate.now().minusDays(i);
            Map<String, Object> row = new HashMap<>();
            row.put("day", day.format(formatter));
            row.put("recordCount", recordService.countByDate(day, role, userId));
            BigDecimal avgScore = recordService.avgByDate(day, role, userId);
            row.put("avgScore", avgScore);
            daily.add(row);
        }
        data.put("daily", daily);
        data.put("status", taskService.statusDistribution(role, userId));
        return data;
    }
}
