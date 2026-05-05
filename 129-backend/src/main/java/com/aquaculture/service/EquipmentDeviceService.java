package com.aquaculture.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aquaculture.entity.EquipmentDevice;
import com.aquaculture.mapper.EquipmentDeviceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentDeviceService extends BaseCrudService<EquipmentDevice> {
    private final EquipmentDeviceMapper equipmentDeviceMapper;

    @Override
    protected BaseMapper<EquipmentDevice> mapper() {
        return equipmentDeviceMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"device_no", "pond_no", "device_name", "device_type"};
    }
}
