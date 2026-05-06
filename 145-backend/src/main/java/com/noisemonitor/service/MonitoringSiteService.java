package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.MonitoringSite;
import com.noisemonitor.mapper.MonitoringSiteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitoringSiteService extends BaseCrudService<MonitoringSite> {
    private final MonitoringSiteMapper supplierProfileMapper;

    @Override
    protected BaseMapper<MonitoringSite> mapper() {
        return supplierProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"supplier_no", "supplier_name", "contact_name", "phone_number"};
    }
}






