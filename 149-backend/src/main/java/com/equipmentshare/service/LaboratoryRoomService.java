package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.LaboratoryRoom;
import com.equipmentshare.mapper.LaboratoryRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LaboratoryRoomService extends BaseCrudService<LaboratoryRoom> {
    private final LaboratoryRoomMapper supplierProfileMapper;

    @Override
    protected BaseMapper<LaboratoryRoom> mapper() {
        return supplierProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"supplier_no", "supplier_name", "contact_name", "phone_number"};
    }
}








