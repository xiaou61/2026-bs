package com.localvoucher.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.localvoucher.entity.MerchantInfo;
import com.localvoucher.mapper.MerchantInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantInfoService extends BaseCrudService<MerchantInfo> {
    private final MerchantInfoMapper mapper;

    @Override
    protected BaseMapper<MerchantInfo> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"merchant_name", "merchant_no", "industry_name", "contact_name"};
    }
}
