package com.cloudmonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmonitor.entity.ResourceGroup;
import com.cloudmonitor.mapper.ResourceGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceGroupService extends BaseCrudService<ResourceGroup> {
    private final ResourceGroupMapper mapper;

    @Override
    protected BaseMapper<ResourceGroup> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"group_name", "group_code", "owner_name", "description"};
    }


}
