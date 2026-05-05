package com.carbonmanage.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbonmanage.entity.ReductionTask;
import com.carbonmanage.mapper.ReductionTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReductionTaskService extends BaseCrudService<ReductionTask> {
    private final ReductionTaskMapper reductionTaskMapper;

    @Override
    protected BaseMapper<ReductionTask> mapper() {
        return reductionTaskMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"task_no", "company_no", "task_name", "owner_name"};
    }
}
