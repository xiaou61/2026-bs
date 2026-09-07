package com.labconsumable.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labconsumable.entity.PurchaseRequest;
import com.labconsumable.mapper.PurchaseRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseRequestService extends BaseCrudService<PurchaseRequest> {
    private final PurchaseRequestMapper purchaseRequestMapper;

    @Override
    protected BaseMapper<PurchaseRequest> mapper() {
        return purchaseRequestMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"request_no", "consumable_name", "request_qty", "applicant_name"};
    }
}
