package com.datamasking.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.RecognitionResult;
import com.datamasking.mapper.RecognitionResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecognitionResultService extends BaseCrudService<RecognitionResult> {
    private final RecognitionResultMapper mapper;

    @Override
    protected BaseMapper<RecognitionResult> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"result_no", "task_no", "field_name", "sensitive_type"};
    }

}
