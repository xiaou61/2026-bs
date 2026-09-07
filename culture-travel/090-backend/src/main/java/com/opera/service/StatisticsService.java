package com.opera.service;

import com.opera.mapper.RepertoireInfoMapper;
import com.opera.mapper.PerformanceScheduleMapper;
import com.opera.mapper.BookingRecordMapper;
import com.opera.mapper.SysUserMapper;
import com.opera.mapper.SystemNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RepertoireInfoMapper repertoireInfoMapper;

    @Autowired
    private PerformanceScheduleMapper performanceScheduleMapper;

    @Autowired
    private BookingRecordMapper bookingRecordMapper;

    @Autowired
    private SystemNoticeMapper noticeMapper;

    @Autowired
    private AuthService authService;

    public Map<String, Object> dashboard(String role) {
        authService.assertTeacher(role);
        Map<String, Object> result = new HashMap<>();
        result.put("userCount", sysUserMapper.countAll());
        result.put("artistCount", sysUserMapper.countByRole("artist"));
        result.put("memberCount", sysUserMapper.countByRole("member"));
        result.put("repertoireCount", repertoireInfoMapper.countAll());
        result.put("performanceCount", performanceScheduleMapper.countAll());
        result.put("bookingCount", bookingRecordMapper.countAll());
        result.put("noticeCount", noticeMapper.countAll());
        return result;
    }
}


