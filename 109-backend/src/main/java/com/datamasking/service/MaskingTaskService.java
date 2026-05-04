package com.datamasking.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.MaskingTask;
import com.datamasking.mapper.MaskingTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaskingTaskService extends BaseCrudService<MaskingTask> {
    private final MaskingTaskMapper mapper;

    @Override
    protected BaseMapper<MaskingTask> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"task_no", "dataset_name", "strategy_name", "owner_name"};
    }

}
