package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.AssetInfo;
import com.assetrfid.mapper.AssetInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetInfoService extends BaseCrudService<AssetInfo> {
    private final AssetInfoMapper consumableCatalogMapper;

    @Override
    protected BaseMapper<AssetInfo> mapper() {
        return consumableCatalogMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"consumable_no", "consumable_name", "spec_model", "unit_name"};
    }
}




