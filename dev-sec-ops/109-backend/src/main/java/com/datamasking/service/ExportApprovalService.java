package com.datamasking.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.datamasking.entity.ExportApproval;
import com.datamasking.mapper.ExportApprovalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExportApprovalService extends BaseCrudService<ExportApproval> {
    private final ExportApprovalMapper mapper;

    @Override
    protected BaseMapper<ExportApproval> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"approval_no", "dataset_name", "export_type", "applicant_name"};
    }

}
