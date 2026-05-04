package com.datamasking.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.MaskingStrategy;
import com.datamasking.mapper.MaskingStrategyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaskingStrategyService extends BaseCrudService<MaskingStrategy> {
    private final MaskingStrategyMapper mapper;

    @Override
    protected BaseMapper<MaskingStrategy> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"strategy_name", "sensitive_type", "masking_method", "review_role"};
    }

}
