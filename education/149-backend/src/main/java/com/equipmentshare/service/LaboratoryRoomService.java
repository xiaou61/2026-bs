package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.LaboratoryRoom;
import com.equipmentshare.mapper.LaboratoryRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LaboratoryRoomService extends BaseCrudService<LaboratoryRoom> {
    private final LaboratoryRoomMapper laboratoryRoomMapper;

    @Override
    protected BaseMapper<LaboratoryRoom> mapper() {
        return laboratoryRoomMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"lab_no", "lab_name", "lab_type", "campus_name"};
    }
}








