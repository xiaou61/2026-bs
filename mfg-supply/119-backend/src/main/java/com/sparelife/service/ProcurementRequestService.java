package com.sparelife.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sparelife.entity.ProcurementRequest;
import com.sparelife.mapper.ProcurementRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcurementRequestService extends BaseCrudService<ProcurementRequest> {
    private final ProcurementRequestMapper mapper;

    @Override
    protected BaseMapper<ProcurementRequest> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"request_no", "part_name", "applicant_name", "approver_name"};
    }
}
