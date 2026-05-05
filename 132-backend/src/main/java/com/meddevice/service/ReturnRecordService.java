package com.meddevice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meddevice.entity.ReturnRecord;
import com.meddevice.mapper.ReturnRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReturnRecordService {
    private final ReturnRecordMapper returnRecordMapper;

    public PageInfo<ReturnRecord> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(returnRecordMapper.selectPage(keyword, status));
    }

    public void save(ReturnRecord entity) {
        if (entity.getId() == null) returnRecordMapper.insert(entity);
        else returnRecordMapper.update(entity);
    }

    public void delete(Long id) {
        returnRecordMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        returnRecordMapper.updateStatus(id, status);
    }
}
