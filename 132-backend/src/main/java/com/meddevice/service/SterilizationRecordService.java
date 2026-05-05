package com.meddevice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meddevice.entity.SterilizationRecord;
import com.meddevice.mapper.SterilizationRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SterilizationRecordService {
    private final SterilizationRecordMapper sterilizationRecordMapper;

    public PageInfo<SterilizationRecord> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(sterilizationRecordMapper.selectPage(keyword, status));
    }

    public void save(SterilizationRecord entity) {
        if (entity.getId() == null) sterilizationRecordMapper.insert(entity);
        else sterilizationRecordMapper.update(entity);
    }

    public void delete(Long id) {
        sterilizationRecordMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        sterilizationRecordMapper.updateStatus(id, status);
    }
}
