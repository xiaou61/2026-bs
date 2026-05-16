package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.EquipmentCategory;
import com.equipmentshare.mapper.EquipmentCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentCategoryService extends BaseCrudService<EquipmentCategory> {
    private final EquipmentCategoryMapper equipmentCategoryMapper;

    @Override
    protected BaseMapper<EquipmentCategory> mapper() {
        return equipmentCategoryMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"category_no", "category_name", "equipment_type", "storage_area"};
    }
}








