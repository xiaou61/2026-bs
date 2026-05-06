package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.ResidentProfile;
import com.timebank.mapper.ResidentProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResidentProfileService extends BaseCrudService<ResidentProfile> {
    private final ResidentProfileMapper labRoomMapper;

    @Override
    protected BaseMapper<ResidentProfile> mapper() {
        return labRoomMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"lab_no", "lab_name", "building_name", "manager_name"};
    }
}





