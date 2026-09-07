package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.HandlingTask;
import com.noisemonitor.mapper.HandlingTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandlingTaskService extends BaseCrudService<HandlingTask> {
    private final HandlingTaskMapper handlingTaskMapper;

    @Override
    protected BaseMapper<HandlingTask> mapper() {
        return handlingTaskMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"task_no", "complaint_title", "assignee_name", "deadline_time"};
    }
}






