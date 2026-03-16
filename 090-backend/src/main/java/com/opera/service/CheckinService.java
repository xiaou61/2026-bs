package com.opera.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opera.common.BusinessException;
import com.opera.entity.CheckinRecord;
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
        entity.setArtistId(userId);
        attendanceMapper.insert(entity);
    }

    public void update(CheckinRecord entity, String role) {
        authService.assertTeacher(role);
        if (entity.getId() == null) {
            throw new BusinessException("签到ID不能为空");
        }
        attendanceMapper.update(entity);
    }
}


