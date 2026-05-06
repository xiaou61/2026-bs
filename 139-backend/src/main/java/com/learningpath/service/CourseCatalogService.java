package com.learningpath.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learningpath.entity.CourseCatalog;
import com.learningpath.mapper.CourseCatalogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseCatalogService extends BaseCrudService<CourseCatalog> {
    private final CourseCatalogMapper supplierProfileMapper;

    @Override
    protected BaseMapper<CourseCatalog> mapper() {
        return supplierProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"supplier_no", "supplier_name", "contact_name", "phone_number"};
    }
}



