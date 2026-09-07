package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.StudyTask;
import com.learningpath.mapper.StudyTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyTaskService extends BaseCrudService<StudyTask> {
    private final StudyTaskMapper studyTaskMapper;

    @Override
    protected BaseMapper<StudyTask> mapper() {
        return studyTaskMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"task_no", "path_name", "task_name", "assignee_name"};
    }
}



