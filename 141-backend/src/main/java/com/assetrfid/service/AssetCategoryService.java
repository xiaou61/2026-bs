package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.AssetCategory;
import com.assetrfid.mapper.AssetCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetCategoryService extends BaseCrudService<AssetCategory> {
    private final AssetCategoryMapper assetCategoryMapper;

    @Override
    protected BaseMapper<AssetCategory> mapper() {
        return assetCategoryMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"category_no", "category_name", "depreciation_method", "manager_name"};
    }
}
