package com.eldercare.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eldercare.entity.ServiceRecord;
import com.eldercare.mapper.ServiceRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceRecordService {
    private final ServiceRecordMapper paymentRecordMapper;

    public PageInfo<ServiceRecord> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(paymentRecordMapper.selectPage(keyword, status));
    }

    public void save(ServiceRecord entity) {
        if (entity.getId() == null) paymentRecordMapper.insert(entity);
        else paymentRecordMapper.update(entity);
    }

    public void delete(Long id) {
        paymentRecordMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        paymentRecordMapper.updateStatus(id, status);
    }
}







