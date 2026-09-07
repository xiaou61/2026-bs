package com.phishingtraining.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.TrainingCourse;
import com.phishingtraining.mapper.TrainingCourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingCourseService extends BaseCrudService<TrainingCourse> {
    private final TrainingCourseMapper mapper;

    @Override
    protected BaseMapper<TrainingCourse> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"course_name", "course_code", "teacher_name", "category_name"};
    }

}
