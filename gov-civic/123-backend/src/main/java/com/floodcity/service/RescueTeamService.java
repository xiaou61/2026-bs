package com.floodcity.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.floodcity.entity.RescueTeam;
import com.floodcity.mapper.RescueTeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RescueTeamService extends BaseCrudService<RescueTeam> {
    private final RescueTeamMapper mapper;

    @Override
    protected BaseMapper<RescueTeam> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"team_no", "team_name", "team_type", "contact_phone"};
    }
}
