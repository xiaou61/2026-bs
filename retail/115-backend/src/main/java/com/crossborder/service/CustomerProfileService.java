package com.crossborder.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crossborder.entity.CustomerProfile;
import com.crossborder.mapper.CustomerProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerProfileService extends BaseCrudService<CustomerProfile> {
    private final CustomerProfileMapper mapper;

    @Override
    protected BaseMapper<CustomerProfile> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"customer_name", "customer_no", "country_region", "email"};
    }
}
