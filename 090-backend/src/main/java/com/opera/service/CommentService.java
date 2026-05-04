package com.opera.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opera.common.BusinessException;
import com.opera.entity.AppreciationComment;
import com.opera.entity.BookingRecord;
import com.opera.entity.PerformanceSchedule;
import com.opera.mapper.AppreciationCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private AppreciationCommentMapper evaluationMapper;

    @Autowired
    private PerformanceService scheduleService;

    @Autowired
    private BookingRecordMapperAdapter bookingRecordMapperAdapter;

    @Autowired
    private AuthService authService;

    public PageInfo<AppreciationComment> list(Long scheduleId, Long userId, String role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AppreciationComment> list;
        if ("member".equals(role)) {
            list = evaluationMapper.selectList(scheduleId, userId, null);
        } else if ("artist".equals(role)) {
            list = evaluationMapper.selectTeacherResult(userId);
        } else {
            list = evaluationMapper.selectList(scheduleId, null, null);
        }
        return new PageInfo<>(list);
    }

    public void add(AppreciationComment entity, Long userId, String role) {
        authService.assertMember(role);
        if (entity == null || entity.getScheduleId() == null) {
            throw new BusinessException("排期不能为空");
        }
        AppreciationComment exists = evaluationMapper.selectByScheduleAndStudent(entity.getScheduleId(), userId);
        if (exists != null) {
            throw new BusinessException("该剧目已提交赏析互动");
        }
        PerformanceSchedule schedule = scheduleService.getById(entity.getScheduleId());
        if (schedule == null) {
            throw new BusinessException("排期不存在");
        }
        BookingRecord bookingRecord = bookingRecordMapperAdapter.selectByScheduleAndStudent(entity.getScheduleId(), userId);
        if (bookingRecord == null) {
            throw new BusinessException(403, "仅已预约会员可提交赏析互动");
        }
        entity.setCourseId(schedule.getCourseId());
        entity.setArtistId(schedule.getArtistId());
        entity.setMemberId(userId);
        evaluationMapper.insert(entity);
    }
}


