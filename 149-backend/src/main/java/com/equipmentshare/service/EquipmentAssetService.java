package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.EquipmentAsset;
import com.equipmentshare.mapper.EquipmentAssetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentAssetService extends BaseCrudService<EquipmentAsset> {
    private final EquipmentAssetMapper consumableCatalogMapper;

    @Override
    protected BaseMapper<EquipmentAsset> mapper() {
        return consumableCatalogMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"consumable_no", "consumable_name", "spec_model", "unit_name"};
    }
}








