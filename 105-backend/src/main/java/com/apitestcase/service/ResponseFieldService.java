package com.apitestcase.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.ResponseField;
import com.apitestcase.mapper.ResponseFieldMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseFieldService extends BaseCrudService<ResponseField> {
    private final ResponseFieldMapper mapper;

    @Override
    protected BaseMapper<ResponseField> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"field_name", "data_type", "parent_path", "example_value"};
    }


}
