package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.ServiceProject;
import com.timebank.mapper.ServiceProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceProjectService extends BaseCrudService<ServiceProject> {
    private final ServiceProjectMapper consumableCatalogMapper;

    @Override
    protected BaseMapper<ServiceProject> mapper() {
        return consumableCatalogMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"consumable_no", "consumable_name", "spec_model", "unit_name"};
    }
}





