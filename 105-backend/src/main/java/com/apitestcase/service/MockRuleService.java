package com.apitestcase.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.MockRule;
import com.apitestcase.mapper.MockRuleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MockRuleService extends BaseCrudService<MockRule> {
    private final MockRuleMapper mapper;

    @Override
    protected BaseMapper<MockRule> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rule_name", "match_expression", "response_body", "status"};
    }


}
