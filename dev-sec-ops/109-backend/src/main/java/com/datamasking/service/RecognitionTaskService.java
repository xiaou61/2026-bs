package com.datamasking.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.RecognitionTask;
import com.datamasking.mapper.RecognitionTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecognitionTaskService extends BaseCrudService<RecognitionTask> {
    private final RecognitionTaskMapper mapper;

    @Override
    protected BaseMapper<RecognitionTask> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"task_no", "dataset_name", "rule_scope", "owner_name"};
    }

}
