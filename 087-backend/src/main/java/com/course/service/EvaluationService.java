package com.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.course.common.BusinessException;
import com.course.entity.CourseEvaluation;
import com.course.entity.CourseSchedule;
import com.course.mapper.CourseEvaluationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {

    @Autowired
    private CourseEvaluationMapper evaluationMapper;

    @Autowired
    private ScheduleService scheduleService;

    public PageInfo<CourseEvaluation> list(Long scheduleId, Long userId, String role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CourseEvaluation> list;
        if ("student".equals(role)) {
            list = evaluationMapper.selectList(scheduleId, userId, null);
        } else if ("teacher".equals(role)) {
            list = evaluationMapper.selectTeacherResult(userId);
        } else {
            list = evaluationMapper.selectList(scheduleId, null, null);
        }
        return new PageInfo<>(list);
    }

    public void add(CourseEvaluation entity, Long userId) {
        if (entity == null || entity.getScheduleId() == null) {
            throw new BusinessException("排课不能为空");
        }
        CourseEvaluation exists = evaluationMapper.selectByScheduleAndStudent(entity.getScheduleId(), userId);
        if (exists != null) {
            throw new BusinessException("该课程已评教");
        }
        CourseSchedule schedule = scheduleService.getById(entity.getScheduleId());
        if (schedule == null) {
            throw new BusinessException("排课不存在");
        }
        entity.setCourseId(schedule.getCourseId());
        entity.setTeacherId(schedule.getTeacherId());
        entity.setStudentId(userId);
        evaluationMapper.insert(entity);
    }
}
