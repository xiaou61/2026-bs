package com.labconsumable.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labconsumable.entity.LabRoom;
import com.labconsumable.mapper.LabRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LabRoomService extends BaseCrudService<LabRoom> {
    private final LabRoomMapper labRoomMapper;

    @Override
    protected BaseMapper<LabRoom> mapper() {
        return labRoomMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"lab_no", "lab_name", "building_name", "manager_name"};
    }
}
