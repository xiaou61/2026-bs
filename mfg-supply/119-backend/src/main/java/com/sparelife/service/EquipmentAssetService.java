package com.sparelife.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sparelife.entity.EquipmentAsset;
import com.sparelife.mapper.EquipmentAssetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentAssetService extends BaseCrudService<EquipmentAsset> {
    private final EquipmentAssetMapper mapper;

    @Override
    protected BaseMapper<EquipmentAsset> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"equipment_no", "equipment_name", "line_name", "asset_level"};
    }
}
