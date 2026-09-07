package com.cloudmonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudmonitor.entity.CloudRegion;
import com.cloudmonitor.mapper.CloudRegionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CloudRegionService extends BaseCrudService<CloudRegion> {
    private final CloudRegionMapper mapper;

    @Override
    protected BaseMapper<CloudRegion> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"region_name", "region_code", "provider_name", "location_name"};
    }


}
