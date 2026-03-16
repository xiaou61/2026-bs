package com.opera.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opera.common.BusinessException;
import com.opera.entity.AppreciationComment;
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

    public void add(AppreciationComment entity, Long userId) {
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
        entity.setCourseId(schedule.getCourseId());
        entity.setArtistId(schedule.getArtistId());
        entity.setMemberId(userId);
        evaluationMapper.insert(entity);
    }
}


