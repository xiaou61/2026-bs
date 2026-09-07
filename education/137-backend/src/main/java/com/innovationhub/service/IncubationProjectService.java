package com.innovationhub.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innovationhub.entity.IncubationProject;
import com.innovationhub.mapper.IncubationProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncubationProjectService extends BaseCrudService<IncubationProject> {
    private final IncubationProjectMapper consumableCatalogMapper;

    @Override
    protected BaseMapper<IncubationProject> mapper() {
        return consumableCatalogMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"consumable_no", "consumable_name", "spec_model", "unit_name"};
    }
}


