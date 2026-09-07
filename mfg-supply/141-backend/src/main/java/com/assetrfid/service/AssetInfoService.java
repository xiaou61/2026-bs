package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.AssetInfo;
import com.assetrfid.mapper.AssetInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetInfoService extends BaseCrudService<AssetInfo> {
    private final AssetInfoMapper assetInfoMapper;

    @Override
    protected BaseMapper<AssetInfo> mapper() {
        return assetInfoMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"asset_no", "asset_name", "asset_model", "department_name"};
    }
}
