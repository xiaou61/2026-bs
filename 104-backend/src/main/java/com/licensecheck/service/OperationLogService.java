package com.licensecheck.service;

import com.licensecheck.entity.OperationLog;
import com.licensecheck.mapper.OperationLogMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationLogService {
    private final OperationLogMapper mapper;

    public PageInfo<OperationLog> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public OperationLog get(Long id) {
        return mapper.selectById(id);
    }

    public void save(OperationLog entity) {

        if (entity.getId() == null) {
            mapper.insert(entity);
        } else {
            mapper.update(entity);
        }
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

}
