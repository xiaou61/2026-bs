package com.equipmentshare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.equipmentshare.entity.BorrowRecord;
import com.equipmentshare.mapper.BorrowRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowRecordService extends BaseCrudService<BorrowRecord> {
    private final BorrowRecordMapper purchaseApprovalMapper;

    @Override
    protected BaseMapper<BorrowRecord> mapper() {
        return purchaseApprovalMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"approval_no", "request_no", "approver_name", "approval_opinion"};
    }
}








