package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.VolunteerProfile;
import com.timebank.mapper.VolunteerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VolunteerProfileService extends BaseCrudService<VolunteerProfile> {
    private final VolunteerProfileMapper volunteerProfileMapper;

    @Override
    protected BaseMapper<VolunteerProfile> mapper() {
        return volunteerProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"volunteer_no", "volunteer_name", "service_expertise", "available_time"};
    }
}

