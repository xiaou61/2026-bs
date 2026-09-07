package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.EquipmentAsset;
import com.equipmentshare.mapper.EquipmentAssetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentAssetService extends BaseCrudService<EquipmentAsset> {
    private final EquipmentAssetMapper equipmentAssetMapper;

    @Override
    protected BaseMapper<EquipmentAsset> mapper() {
        return equipmentAssetMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"asset_no", "asset_name", "asset_model", "laboratory_name"};
    }
}








