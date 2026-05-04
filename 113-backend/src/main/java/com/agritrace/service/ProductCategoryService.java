package com.agritrace.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.agritrace.entity.ProductCategory;
import com.agritrace.mapper.ProductCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCategoryService extends BaseCrudService<ProductCategory> {
    private final ProductCategoryMapper mapper;

    @Override
    protected BaseMapper<ProductCategory> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"category_name", "category_code", "standard_name", "owner_name"};
    }

}
