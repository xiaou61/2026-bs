package com.crossborder.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crossborder.entity.ExchangeRate;
import com.crossborder.mapper.ExchangeRateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateService extends BaseCrudService<ExchangeRate> {
    private final ExchangeRateMapper mapper;

    @Override
    protected BaseMapper<ExchangeRate> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"rate_no", "base_currency", "target_currency", "provider_name"};
    }
}
