package com.crossborder.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crossborder.entity.ReconciliationTask;
import com.crossborder.mapper.ReconciliationTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReconciliationTaskService extends BaseCrudService<ReconciliationTask> {
    private final ReconciliationTaskMapper mapper;

    @Override
    protected BaseMapper<ReconciliationTask> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"task_no", "bill_no", "check_result", "status"};
    }
}
