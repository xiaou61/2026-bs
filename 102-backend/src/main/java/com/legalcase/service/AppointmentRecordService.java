package com.legalcase.service;

import com.legalcase.common.BusinessException;
import com.legalcase.entity.AppointmentRecord;
import com.legalcase.mapper.AppointmentRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentRecordService {
    @Autowired
    private AppointmentRecordMapper mapper;

    public PageInfo<AppointmentRecord> page(Integer pageNum, Integer pageSize, String keyword, Long caseId, Long clientId, Long lawyerId, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, caseId, clientId, lawyerId, status));
    }

    public AppointmentRecord getById(Long id) {
        return mapper.selectById(id);
    }

    public void updateStatus(Long id, Integer status) {
        AppointmentRecord entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException(400, "预约记录不存在");
        }
        entity.setStatus(status);
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
