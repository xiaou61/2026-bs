package com.kindergarten.service;

import com.kindergarten.mapper.ActivityInfoMapper;
import com.kindergarten.mapper.ActivityScheduleMapper;
import com.kindergarten.mapper.ChildProfileMapper;
import com.kindergarten.mapper.SysUserMapper;
import com.kindergarten.mapper.SystemNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ActivityInfoMapper activityInfoMapper;

    @Autowired
    private ActivityScheduleMapper scheduleMapper;

    @Autowired
    private ChildProfileMapper childProfileMapper;

    @Autowired
    private SystemNoticeMapper noticeMapper;

    @Autowired
    private AuthService authService;

    public Map<String, Object> dashboard(String role) {
        authService.assertTeacher(role);
        Map<String, Object> result = new HashMap<>();
        result.put("userCount", sysUserMapper.countAll());
        result.put("teacherCount", sysUserMapper.countByRole("teacher"));
        result.put("parentCount", sysUserMapper.countByRole("parent"));
        result.put("childCount", childProfileMapper.countAll());
        result.put("activityCount", activityInfoMapper.countAll());
        result.put("scheduleCount", scheduleMapper.countAll());
        result.put("noticeCount", noticeMapper.countAll());
        return result;
    }
}
