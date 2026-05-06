package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.CounselRecord;
import com.psychologycare.mapper.CounselRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounselRecordService extends BaseCrudService<CounselRecord> {
    private final CounselRecordMapper purchaseApprovalMapper;

    @Override
    protected BaseMapper<CounselRecord> mapper() {
        return purchaseApprovalMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"approval_no", "request_no", "approver_name", "approval_opinion"};
    }
}







