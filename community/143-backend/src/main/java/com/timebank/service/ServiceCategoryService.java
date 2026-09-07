package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.ServiceCategory;
import com.timebank.mapper.ServiceCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceCategoryService extends BaseCrudService<ServiceCategory> {
    private final ServiceCategoryMapper serviceCategoryMapper;

    @Override
    protected BaseMapper<ServiceCategory> mapper() {
        return serviceCategoryMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"category_no", "category_name", "service_type", "target_group"};
    }
}

