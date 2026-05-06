package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.RfidTag;
import com.assetrfid.mapper.RfidTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RfidTagService extends BaseCrudService<RfidTag> {
    private final RfidTagMapper labRoomMapper;

    @Override
    protected BaseMapper<RfidTag> mapper() {
        return labRoomMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"lab_no", "lab_name", "building_name", "manager_name"};
    }
}




