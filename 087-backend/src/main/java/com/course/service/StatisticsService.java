package com.course.service;

import com.course.mapper.CourseInfoMapper;
import com.course.mapper.CourseScheduleMapper;
import com.course.mapper.CourseSelectionMapper;
import com.course.mapper.SysUserMapper;
import com.course.mapper.SystemNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Autowired
    private CourseScheduleMapper scheduleMapper;

    @Autowired
    private CourseSelectionMapper selectionMapper;

    @Autowired
    private SystemNoticeMapper noticeMapper;

    @Autowired
    private AuthService authService;

    public Map<String, Object> dashboard(String role) {
        authService.assertTeacher(role);
        Map<String, Object> result = new HashMap<>();
        result.put("userCount", sysUserMapper.countAll());
        result.put("teacherCount", sysUserMapper.countByRole("teacher"));
        result.put("studentCount", sysUserMapper.countByRole("student"));
        result.put("courseCount", courseInfoMapper.countAll());
        result.put("scheduleCount", scheduleMapper.countAll());
        result.put("selectionCount", selectionMapper.countAll());
        result.put("noticeCount", noticeMapper.countAll());
        return result;
    }
}
