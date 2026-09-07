package com.labconsumable.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labconsumable.entity.OperationLog;
import com.labconsumable.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationLogService extends BaseCrudService<OperationLog> {
    private final OperationLogMapper operationLogMapper;

    @Override
    protected BaseMapper<OperationLog> mapper() {
        return operationLogMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"operator_name", "module_name", "action_type", "target_name"};
    }
}
