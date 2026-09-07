package com.labconsumable.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labconsumable.entity.PurchaseApproval;
import com.labconsumable.mapper.PurchaseApprovalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseApprovalService extends BaseCrudService<PurchaseApproval> {
    private final PurchaseApprovalMapper purchaseApprovalMapper;

    @Override
    protected BaseMapper<PurchaseApproval> mapper() {
        return purchaseApprovalMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"approval_no", "request_no", "approver_name", "approval_opinion"};
    }
}
