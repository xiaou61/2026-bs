package com.phishingtraining.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.PhishingCampaign;
import com.phishingtraining.mapper.PhishingCampaignMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhishingCampaignService extends BaseCrudService<PhishingCampaign> {
    private final PhishingCampaignMapper mapper;

    @Override
    protected BaseMapper<PhishingCampaign> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"campaign_name", "campaign_no", "template_name", "owner_name"};
    }

}
