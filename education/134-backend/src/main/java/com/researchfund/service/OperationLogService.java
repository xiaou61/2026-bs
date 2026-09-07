package com.researchfund.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.researchfund.entity.OperationLog;
import com.researchfund.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationLogService {
    private final OperationLogMapper operationLogMapper;

    public PageInfo<OperationLog> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(operationLogMapper.selectPage(keyword, status));
    }

    public void save(OperationLog entity) {
        if (entity.getId() == null) operationLogMapper.insert(entity);
        else operationLogMapper.update(entity);
    }

    public void delete(Long id) {
        operationLogMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        operationLogMapper.updateStatus(id, status);
    }
}
