package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.ServiceCategory;
import com.timebank.mapper.ServiceCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceCategoryService extends BaseCrudService<ServiceCategory> {
    private final ServiceCategoryMapper supplierProfileMapper;

    @Override
    protected BaseMapper<ServiceCategory> mapper() {
        return supplierProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"supplier_no", "supplier_name", "contact_name", "phone_number"};
    }
}





