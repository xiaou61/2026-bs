package com.noisemonitor.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noisemonitor.entity.HandlingTask;
import com.noisemonitor.mapper.HandlingTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandlingTaskService extends BaseCrudService<HandlingTask> {
    private final HandlingTaskMapper purchaseRequestMapper;

    @Override
    protected BaseMapper<HandlingTask> mapper() {
        return purchaseRequestMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"request_no", "consumable_name", "request_qty", "applicant_name"};
    }
}






