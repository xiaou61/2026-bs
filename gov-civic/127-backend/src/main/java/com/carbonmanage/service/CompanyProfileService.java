package com.carbonmanage.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbonmanage.entity.CompanyProfile;
import com.carbonmanage.mapper.CompanyProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyProfileService extends BaseCrudService<CompanyProfile> {
    private final CompanyProfileMapper companyProfileMapper;

    @Override
    protected BaseMapper<CompanyProfile> mapper() {
        return companyProfileMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"company_no", "company_name", "industry_name", "region_name"};
    }
}
