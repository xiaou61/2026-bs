package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.AssetCategory;
import com.assetrfid.mapper.AssetCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetCategoryService extends BaseCrudService<AssetCategory> {
    private final AssetCategoryMapper supplierProfileMapper;

    @Override
    protected BaseMapper<AssetCategory> mapper() {
        return supplierProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"supplier_no", "supplier_name", "contact_name", "phone_number"};
    }
}




