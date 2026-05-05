package com.greenhouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greenhouse.entity.IrrigationTask;
import com.greenhouse.mapper.IrrigationTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IrrigationTaskService {
    private final IrrigationTaskMapper irrigationTaskMapper;

    public PageInfo<IrrigationTask> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(irrigationTaskMapper.selectPage(keyword, status));
    }

    public void save(IrrigationTask entity) {
        if (entity.getId() == null) irrigationTaskMapper.insert(entity);
        else irrigationTaskMapper.update(entity);
    }

    public void delete(Long id) {
        irrigationTaskMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        irrigationTaskMapper.updateStatus(id, status);
    }
}
