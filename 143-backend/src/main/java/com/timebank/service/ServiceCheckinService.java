package com.timebank.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.timebank.entity.ServiceCheckin;
import com.timebank.mapper.ServiceCheckinMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceCheckinService extends BaseCrudService<ServiceCheckin> {
    private final ServiceCheckinMapper purchaseApprovalMapper;

    @Override
    protected BaseMapper<ServiceCheckin> mapper() {
        return purchaseApprovalMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"approval_no", "request_no", "approver_name", "approval_opinion"};
    }
}





