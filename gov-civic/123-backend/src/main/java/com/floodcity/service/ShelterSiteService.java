package com.floodcity.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.floodcity.entity.ShelterSite;
import com.floodcity.mapper.ShelterSiteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShelterSiteService extends BaseCrudService<ShelterSite> {
    private final ShelterSiteMapper mapper;

    @Override
    protected BaseMapper<ShelterSite> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"site_no", "site_name", "address_name", "manager_name"};
    }
}
