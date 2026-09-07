package com.carbonmanage.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbonmanage.entity.CarbonQuota;
import com.carbonmanage.mapper.CarbonQuotaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarbonQuotaService extends BaseCrudService<CarbonQuota> {
    private final CarbonQuotaMapper carbonQuotaMapper;

    @Override
    protected BaseMapper<CarbonQuota> mapper() {
        return carbonQuotaMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"quota_no", "company_no"};
    }
}
