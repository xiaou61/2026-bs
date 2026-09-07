package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.OfficerProfile;
import com.noisemonitor.mapper.OfficerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfficerProfileService extends BaseCrudService<OfficerProfile> {
    private final OfficerProfileMapper officerProfileMapper;

    @Override
    protected BaseMapper<OfficerProfile> mapper() {
        return officerProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"officer_no", "officer_name", "duty_area", "contact_phone"};
    }
}






