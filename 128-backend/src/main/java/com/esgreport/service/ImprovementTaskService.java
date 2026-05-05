package com.esgreport.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.esgreport.entity.ImprovementTask;
import com.esgreport.mapper.ImprovementTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImprovementTaskService {
    private final ImprovementTaskMapper improvementTaskMapper;

    public PageInfo<ImprovementTask> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(improvementTaskMapper.selectPage(keyword, status));
    }

    public void save(ImprovementTask entity) {
        if (entity.getId() == null) improvementTaskMapper.insert(entity);
        else improvementTaskMapper.update(entity);
    }

    public void delete(Long id) {
        improvementTaskMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        improvementTaskMapper.updateStatus(id, status);
    }
}
