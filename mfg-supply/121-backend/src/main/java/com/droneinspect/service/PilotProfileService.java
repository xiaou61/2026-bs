package com.droneinspect.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.droneinspect.entity.PilotProfile;
import com.droneinspect.mapper.PilotProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PilotProfileService extends BaseCrudService<PilotProfile> {
    private final PilotProfileMapper mapper;

    @Override
    protected BaseMapper<PilotProfile> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"pilot_no", "pilot_name", "license_level", "team_name"};
    }
}
