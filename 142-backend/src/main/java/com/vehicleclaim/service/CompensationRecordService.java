package com.vehicleclaim.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vehicleclaim.entity.CompensationRecord;
import com.vehicleclaim.mapper.CompensationRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompensationRecordService {
    private final CompensationRecordMapper paperRecordMapper;

    public PageInfo<CompensationRecord> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(paperRecordMapper.selectPage(keyword, status));
    }

    public void save(CompensationRecord entity) {
        if (entity.getId() == null) paperRecordMapper.insert(entity);
        else paperRecordMapper.update(entity);
    }

    public void delete(Long id) {
        paperRecordMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        paperRecordMapper.updateStatus(id, status);
    }
}




