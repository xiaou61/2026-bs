package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.StudyTask;
import com.learningpath.mapper.StudyTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyTaskService extends BaseCrudService<StudyTask> {
    private final StudyTaskMapper purchaseRequestMapper;

    @Override
    protected BaseMapper<StudyTask> mapper() {
        return purchaseRequestMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"request_no", "consumable_name", "request_qty", "applicant_name"};
    }
}



