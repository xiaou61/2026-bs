package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.FeeRecord;
import com.legalcase.mapper.FeeRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FeeRecordService {
    @Autowired
    private FeeRecordMapper mapper;

    public PageInfo<FeeRecord> page(Integer pageNum, Integer pageSize, String keyword, Long caseId, Long clientId, Integer payStatus) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, caseId, clientId, payStatus));
    }

    public FeeRecord getById(Long id) {
        return mapper.selectById(id);
    }

    public void pay(Long id) {
        FeeRecord entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "费用记录不存在");
        }
        entity.setPayStatus(1);
        entity.setPayTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void refund(Long id) {
        FeeRecord entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "费用记录不存在");
        }
        entity.setPayStatus(2);
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateById(entity);
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public long countAll() {
        return mapper.countAll();
    }
}
