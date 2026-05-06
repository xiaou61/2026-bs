package com.outpatientexam.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.outpatientexam.entity.QueueCall;
import com.outpatientexam.mapper.QueueCallMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueCallService {
    private final QueueCallMapper performanceStatisticMapper;

    public PageInfo<QueueCall> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(performanceStatisticMapper.selectPage(keyword, status));
    }

    public void save(QueueCall entity) {
        if (entity.getId() == null) performanceStatisticMapper.insert(entity);
        else performanceStatisticMapper.update(entity);
    }

    public void delete(Long id) {
        performanceStatisticMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        performanceStatisticMapper.updateStatus(id, status);
    }
}








