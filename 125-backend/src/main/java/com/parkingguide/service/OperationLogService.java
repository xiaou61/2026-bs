package com.parkingguide.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.parkingguide.entity.OperationLog;
import com.parkingguide.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationLogService extends BaseCrudService<OperationLog> {
    private final OperationLogMapper mapper;

    @Override
    protected BaseMapper<OperationLog> mapper() {
        return mapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"operator_name", "module_name", "action_type", "target_name"};
    }
}
