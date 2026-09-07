package com.apitestcase.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.ApiEndpoint;
import com.apitestcase.mapper.ApiEndpointMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiEndpointService extends BaseCrudService<ApiEndpoint> {
    private final ApiEndpointMapper mapper;

    @Override
    protected BaseMapper<ApiEndpoint> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"api_name", "method", "path_url", "content_type"};
    }


}
