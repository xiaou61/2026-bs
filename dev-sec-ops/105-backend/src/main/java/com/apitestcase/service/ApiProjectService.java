package com.apitestcase.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.ApiProject;
import com.apitestcase.mapper.ApiProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiProjectService extends BaseCrudService<ApiProject> {
    private final ApiProjectMapper mapper;

    @Override
    protected BaseMapper<ApiProject> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"project_name", "project_code", "owner_name", "base_url"};
    }


}
