package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.ConsultationRecord;
import com.legalcase.mapper.ConsultationRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsultationRecordService {
    @Autowired
    private ConsultationRecordMapper mapper;

    public PageInfo<ConsultationRecord> page(Integer pageNum, Integer pageSize, String keyword, Long caseId, Long clientId, Long lawyerId, String riskLevel) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, caseId, clientId, lawyerId, riskLevel));
    }

    public ConsultationRecord getById(Long id) {
        return mapper.selectById(id);
    }

    public void saveEntity(ConsultationRecord entity) {
        if (entity.getId() == null) {
            
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        if (entity.getId() == null) {
            mapper.insert(entity);
        } else {
            mapper.updateById(entity);
        }
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public long countAll() {
        return mapper.countAll();
    }
}
