package com.phishingtraining.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.DepartmentGroup;
import com.phishingtraining.mapper.DepartmentGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentGroupService extends BaseCrudService<DepartmentGroup> {
    private final DepartmentGroupMapper mapper;

    @Override
    protected BaseMapper<DepartmentGroup> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"group_name", "group_code", "leader_name", "business_line"};
    }

}
