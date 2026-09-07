package com.agritrace.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.agritrace.entity.FarmerProfile;
import com.agritrace.mapper.FarmerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FarmerProfileService extends BaseCrudService<FarmerProfile> {
    private final FarmerProfileMapper mapper;

    @Override
    protected BaseMapper<FarmerProfile> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"farmer_name", "farmer_no", "cooperative_name", "region_name"};
    }

}
