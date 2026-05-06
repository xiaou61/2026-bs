package com.foodinspect.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.foodinspect.entity.DisposalRecord;
import com.foodinspect.mapper.DisposalRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisposalRecordService {
    private final DisposalRecordMapper paperRecordMapper;

    public PageInfo<DisposalRecord> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(paperRecordMapper.selectPage(keyword, status));
    }

    public void save(DisposalRecord entity) {
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






