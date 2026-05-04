package com.phishingtraining.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phishingtraining.entity.ClickTracking;
import com.phishingtraining.mapper.ClickTrackingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClickTrackingService extends BaseCrudService<ClickTracking> {
    private final ClickTrackingMapper mapper;

    @Override
    protected BaseMapper<ClickTracking> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"track_no", "campaign_name", "employee_name", "client_ip"};
    }

}
