package com.apitestcase.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.RequestParam;
import com.apitestcase.mapper.RequestParamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestParamService extends BaseCrudService<RequestParam> {
    private final RequestParamMapper mapper;

    @Override
    protected BaseMapper<RequestParam> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"param_name", "param_type", "data_type", "example_value"};
    }


}
