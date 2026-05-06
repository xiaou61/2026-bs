package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.PatrolRecord;
import com.noisemonitor.mapper.PatrolRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatrolRecordService extends BaseCrudService<PatrolRecord> {
    private final PatrolRecordMapper purchaseApprovalMapper;

    @Override
    protected BaseMapper<PatrolRecord> mapper() {
        return purchaseApprovalMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"approval_no", "request_no", "approver_name", "approval_opinion"};
    }
}






