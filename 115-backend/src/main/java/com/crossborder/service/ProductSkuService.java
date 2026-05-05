package com.crossborder.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crossborder.entity.ProductSku;
import com.crossborder.mapper.ProductSkuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSkuService extends BaseCrudService<ProductSku> {
    private final ProductSkuMapper mapper;

    @Override
    protected BaseMapper<ProductSku> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"sku_no", "product_name", "hs_code", "category_name"};
    }
}
