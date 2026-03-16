package com.course.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.course.common.BusinessException;
import com.course.entity.CourseSchedule;
import com.course.entity.CourseSelection;
import com.course.mapper.CourseSelectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectionService {

    @Autowired
    private CourseSelectionMapper selectionMapper;

    @Autowired
    private ScheduleService scheduleService;

    public PageInfo<CourseSelection> list(Long scheduleId, Long studentId, Integer selectStatus, String role, Long currentUserId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Long targetStudentId = "student".equals(role) ? currentUserId : studentId;
        Long teacherId = "teacher".equals(role) ? currentUserId : null;
        List<CourseSelection> list = selectionMapper.selectList(scheduleId, targetStudentId, selectStatus, teacherId);
        return new PageInfo<>(list);
    }

    public void selectCourse(Long scheduleId, Long studentId) {
        CourseSchedule schedule = scheduleService.getById(scheduleId);
        if (schedule == null) {
            throw new BusinessException("排课不存在");
        }
        CourseSelection exists = selectionMapper.selectByScheduleAndStudent(scheduleId, studentId);
        if (exists != null) {
            throw new BusinessException("该课程已选");
        }
        if (schedule.getMaxStudentCount() != null && schedule.getSelectedCount() != null && schedule.getSelectedCount() >= schedule.getMaxStudentCount()) {
            throw new BusinessException("该课程名额已满");
        }
        CourseSelection selection = new CourseSelection();
        selection.setScheduleId(scheduleId);
        selection.setCourseId(schedule.getCourseId());
        selection.setStudentId(studentId);
        selection.setSelectStatus(1);
        selectionMapper.insert(selection);
        scheduleService.increaseSelectedCount(scheduleId);
    }
}
