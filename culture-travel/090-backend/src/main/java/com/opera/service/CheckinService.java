package com.opera.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opera.common.BusinessException;
import com.opera.entity.CheckinRecord;
import com.opera.entity.PerformanceSchedule;
import com.opera.mapper.CheckinRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckinService {

    @Autowired
    private CheckinRecordMapper attendanceMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private PerformanceService performanceService;

    public PageInfo<CheckinRecord> list(Long scheduleId, Long memberId, Long userId, String role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CheckinRecord> list = attendanceMapper.selectList(scheduleId, "member".equals(role) ? userId : memberId, "artist".equals(role) ? userId : null);
        return new PageInfo<>(list);
    }

    public void add(CheckinRecord entity, Long userId, String role) {
        authService.assertTeacher(role);
        if (entity == null || entity.getScheduleId() == null || entity.getCourseId() == null || entity.getMemberId() == null) {
            throw new BusinessException("排期、剧目、会员不能为空");
        }
        PerformanceSchedule schedule = performanceService.getById(entity.getScheduleId());
        if (schedule == null) {
            throw new BusinessException("排期不存在");
        }
        if ("artist".equals(role) && !userId.equals(schedule.getArtistId())) {
            throw new BusinessException(403, "无权限操作");
        }
        entity.setArtistId(userId);
        attendanceMapper.insert(entity);
    }

    public void update(CheckinRecord entity, Long userId, String role) {
        authService.assertTeacher(role);
        if (entity.getId() == null) {
            throw new BusinessException("签到ID不能为空");
        }
        CheckinRecord db = attendanceMapper.selectById(entity.getId());
        if (db == null) {
            throw new BusinessException("签到记录不存在");
        }
        if ("artist".equals(role) && !userId.equals(db.getArtistId())) {
            throw new BusinessException(403, "无权限操作");
        }
        attendanceMapper.update(entity);
    }
}


