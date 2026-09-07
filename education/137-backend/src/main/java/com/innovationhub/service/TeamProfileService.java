package com.innovationhub.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.innovationhub.entity.TeamProfile;
import com.innovationhub.mapper.TeamProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamProfileService extends BaseCrudService<TeamProfile> {
    private final TeamProfileMapper labRoomMapper;

    @Override
    protected BaseMapper<TeamProfile> mapper() {
        return labRoomMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"lab_no", "lab_name", "building_name", "manager_name"};
    }
}


