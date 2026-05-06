package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.EquipmentCategory;
import com.equipmentshare.mapper.EquipmentCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentCategoryService extends BaseCrudService<EquipmentCategory> {
    private final EquipmentCategoryMapper labRoomMapper;

    @Override
    protected BaseMapper<EquipmentCategory> mapper() {
        return labRoomMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"lab_no", "lab_name", "building_name", "manager_name"};
    }
}








