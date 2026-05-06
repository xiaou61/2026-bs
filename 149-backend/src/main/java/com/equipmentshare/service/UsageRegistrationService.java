package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.UsageRegistration;
import com.equipmentshare.mapper.UsageRegistrationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsageRegistrationService extends BaseCrudService<UsageRegistration> {
    private final UsageRegistrationMapper purchaseOrderMapper;

    @Override
    protected BaseMapper<UsageRegistration> mapper() {
        return purchaseOrderMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"order_no", "supplier_name", "consumable_name", "order_amount"};
    }
}








