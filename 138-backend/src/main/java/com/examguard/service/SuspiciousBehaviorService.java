package com.examguard.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.examguard.entity.SuspiciousBehavior;
import com.examguard.mapper.SuspiciousBehaviorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuspiciousBehaviorService {
    private final SuspiciousBehaviorMapper invoiceRecordMapper;

    public PageInfo<SuspiciousBehavior> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(invoiceRecordMapper.selectPage(keyword, status));
    }

    public void save(SuspiciousBehavior entity) {
        if (entity.getId() == null) invoiceRecordMapper.insert(entity);
        else invoiceRecordMapper.update(entity);
    }

    public void delete(Long id) {
        invoiceRecordMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        invoiceRecordMapper.updateStatus(id, status);
    }
}


