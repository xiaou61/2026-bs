package com.apitestcase.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.apitestcase.entity.OperationLog;
import com.apitestcase.mapper.OperationLogMapper;
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
        return new String[]{"username", "action", "target_type", "detail"};
    }

@Override
protected String statusColumn() {
    return null;
}


}
