package com.foodinspect.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.foodinspect.entity.SampleRecord;
import com.foodinspect.mapper.SampleRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleRecordService {
    private final SampleRecordMapper approvalTaskMapper;

    public PageInfo<SampleRecord> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(approvalTaskMapper.selectPage(keyword, status));
    }

    public void save(SampleRecord entity) {
        if (entity.getId() == null) approvalTaskMapper.insert(entity);
        else approvalTaskMapper.update(entity);
    }

    public void delete(Long id) {
        approvalTaskMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        approvalTaskMapper.updateStatus(id, status);
    }
}






