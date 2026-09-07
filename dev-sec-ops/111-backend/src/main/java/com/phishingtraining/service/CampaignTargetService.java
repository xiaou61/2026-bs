package com.phishingtraining.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.CampaignTarget;
import com.phishingtraining.mapper.CampaignTargetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CampaignTargetService extends BaseCrudService<CampaignTarget> {
    private final CampaignTargetMapper mapper;

    @Override
    protected BaseMapper<CampaignTarget> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"campaign_name", "employee_name", "email", "department_name"};
    }

}
