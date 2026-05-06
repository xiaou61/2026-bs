package com.examguard.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.examguard.entity.EvidenceRecord;
import com.examguard.mapper.EvidenceRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvidenceRecordService {
    private final EvidenceRecordMapper approvalTaskMapper;

    public PageInfo<EvidenceRecord> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(approvalTaskMapper.selectPage(keyword, status));
    }

    public void save(EvidenceRecord entity) {
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


