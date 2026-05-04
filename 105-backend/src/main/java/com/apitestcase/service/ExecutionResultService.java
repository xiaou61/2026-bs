package com.apitestcase.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.ExecutionResult;
import com.apitestcase.mapper.ExecutionResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExecutionResultService extends BaseCrudService<ExecutionResult> {
    private final ExecutionResultMapper mapper;

    @Override
    protected BaseMapper<ExecutionResult> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"result_status", "assert_message", "status"};
    }


}
