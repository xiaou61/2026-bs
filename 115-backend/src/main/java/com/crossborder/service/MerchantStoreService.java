package com.crossborder.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crossborder.entity.MerchantStore;
import com.crossborder.mapper.MerchantStoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantStoreService extends BaseCrudService<MerchantStore> {
    private final MerchantStoreMapper mapper;

    @Override
    protected BaseMapper<MerchantStore> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"store_name", "store_code", "platform_name", "company_name"};
    }
}
