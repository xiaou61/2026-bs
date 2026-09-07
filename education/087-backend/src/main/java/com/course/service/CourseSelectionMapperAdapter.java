package com.course.service;

import com.course.entity.CourseSelection;
import com.course.entity.CourseSchedule;
import com.course.mapper.CourseSelectionMapper;
import com.course.mapper.CourseScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseSelectionMapperAdapter {

    @Autowired
    private CourseSelectionMapper selectionMapper;

    @Autowired
    private CourseScheduleMapper scheduleMapper;

    public CourseSelection getById(Long id) {
        return selectionMapper.selectById(id);
    }

    public CourseSchedule getScheduleById(Long id) {
        return scheduleMapper.selectById(id);
    }
}
