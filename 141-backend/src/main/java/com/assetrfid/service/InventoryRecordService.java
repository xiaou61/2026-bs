package com.assetrfid.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.assetrfid.entity.InventoryRecord;
import com.assetrfid.mapper.InventoryRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryRecordService extends BaseCrudService<InventoryRecord> {
    private final InventoryRecordMapper purchaseApprovalMapper;

    @Override
    protected BaseMapper<InventoryRecord> mapper() {
        return purchaseApprovalMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"approval_no", "request_no", "approver_name", "approval_opinion"};
    }
}




