package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.ResidentProfile;
import com.timebank.mapper.ResidentProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResidentProfileService extends BaseCrudService<ResidentProfile> {
    private final ResidentProfileMapper residentProfileMapper;

    @Override
    protected BaseMapper<ResidentProfile> mapper() {
        return residentProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"resident_no", "resident_name", "community_name", "service_preference"};
    }
}

