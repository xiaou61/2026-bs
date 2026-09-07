package com.drugreaction.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.drugreaction.entity.ReporterProfile;
import com.drugreaction.mapper.ReporterProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReporterProfileService extends BaseCrudService<ReporterProfile> {
    private final ReporterProfileMapper reporterProfileMapper;

    @Override
    protected BaseMapper<ReporterProfile> mapper() {
        return reporterProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"reporter_no", "reporter_name", "organization_name", "phone_number"};
    }
}
