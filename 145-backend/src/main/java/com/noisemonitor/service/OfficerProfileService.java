package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.OfficerProfile;
import com.noisemonitor.mapper.OfficerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfficerProfileService extends BaseCrudService<OfficerProfile> {
    private final OfficerProfileMapper stockItemMapper;

    @Override
    protected BaseMapper<OfficerProfile> mapper() {
        return stockItemMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"stock_no", "consumable_name", "lab_name", "current_qty"};
    }
}






