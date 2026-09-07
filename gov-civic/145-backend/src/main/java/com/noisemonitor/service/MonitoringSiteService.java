package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.MonitoringSite;
import com.noisemonitor.mapper.MonitoringSiteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitoringSiteService extends BaseCrudService<MonitoringSite> {
    private final MonitoringSiteMapper monitoringSiteMapper;

    @Override
    protected BaseMapper<MonitoringSite> mapper() {
        return monitoringSiteMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"site_no", "site_name", "site_type", "street_name"};
    }
}






