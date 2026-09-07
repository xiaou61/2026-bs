package com.aquaculture.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aquaculture.entity.PondProfile;
import com.aquaculture.mapper.PondProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PondProfileService extends BaseCrudService<PondProfile> {
    private final PondProfileMapper pondProfileMapper;

    @Override
    protected BaseMapper<PondProfile> mapper() {
        return pondProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"pond_no", "pond_name", "farm_area", "manager_name"};
    }
}
