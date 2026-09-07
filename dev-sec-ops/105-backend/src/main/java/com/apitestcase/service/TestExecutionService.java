package com.apitestcase.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.TestExecution;
import com.apitestcase.mapper.TestExecutionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestExecutionService extends BaseCrudService<TestExecution> {
    private final TestExecutionMapper mapper;

    @Override
    protected BaseMapper<TestExecution> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"execution_no", "env_name", "executor", "status"};
    }


}
