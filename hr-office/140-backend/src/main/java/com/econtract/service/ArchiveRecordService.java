package com.econtract.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.econtract.entity.ArchiveRecord;
import com.econtract.mapper.ArchiveRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArchiveRecordService {
    private final ArchiveRecordMapper archiveRecordMapper;

    public PageInfo<ArchiveRecord> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(archiveRecordMapper.selectPage(keyword, status));
    }

    public void save(ArchiveRecord entity) {
        if (entity.getId() == null) archiveRecordMapper.insert(entity);
        else archiveRecordMapper.update(entity);
    }

    public void delete(Long id) {
        archiveRecordMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        archiveRecordMapper.updateStatus(id, status);
    }
}



