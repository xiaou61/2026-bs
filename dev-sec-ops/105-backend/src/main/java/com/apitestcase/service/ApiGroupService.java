package com.apitestcase.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.ApiGroup;
import com.apitestcase.mapper.ApiGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiGroupService extends BaseCrudService<ApiGroup> {
    private final ApiGroupMapper mapper;

    @Override
    protected BaseMapper<ApiGroup> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"group_name", "parent_name", "description", "status"};
    }


}
