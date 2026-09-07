package com.apitestcase.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.EnvironmentConfig;
import com.apitestcase.mapper.EnvironmentConfigMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnvironmentConfigService extends BaseCrudService<EnvironmentConfig> {
    private final EnvironmentConfigMapper mapper;

    @Override
    protected BaseMapper<EnvironmentConfig> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"env_name", "base_url", "headers", "variables"};
    }


}
