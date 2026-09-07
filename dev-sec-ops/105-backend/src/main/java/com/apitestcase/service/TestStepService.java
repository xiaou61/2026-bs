package com.apitestcase.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.TestStep;
import com.apitestcase.mapper.TestStepMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestStepService extends BaseCrudService<TestStep> {
    private final TestStepMapper mapper;

    @Override
    protected BaseMapper<TestStep> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"step_name", "request_data", "assert_expression", "extract_variable"};
    }


}
