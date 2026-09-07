package com.apitestcase.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.TestCase;
import com.apitestcase.mapper.TestCaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestCaseService extends BaseCrudService<TestCase> {
    private final TestCaseMapper mapper;

    @Override
    protected BaseMapper<TestCase> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"case_name", "case_type", "priority", "request_body"};
    }


}
