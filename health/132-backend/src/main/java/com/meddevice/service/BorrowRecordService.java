package com.meddevice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meddevice.entity.BorrowRecord;
import com.meddevice.mapper.BorrowRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowRecordService {
    private final BorrowRecordMapper borrowRecordMapper;

    public PageInfo<BorrowRecord> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(borrowRecordMapper.selectPage(keyword, status));
    }

    public void save(BorrowRecord entity) {
        if (entity.getId() == null) borrowRecordMapper.insert(entity);
        else borrowRecordMapper.update(entity);
    }

    public void delete(Long id) {
        borrowRecordMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        borrowRecordMapper.updateStatus(id, status);
    }
}
