package com.course.service;

import com.course.entity.CourseSelection;
import com.course.mapper.CourseSelectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseSelectionMapperAdapter {

    @Autowired
    private CourseSelectionMapper selectionMapper;

    public CourseSelection getById(Long id) {
        return selectionMapper.selectById(id);
    }
}
