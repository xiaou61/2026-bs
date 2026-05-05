package com.worksite.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.worksite.entity.SafetyTraining;
import com.worksite.mapper.SafetyTrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SafetyTrainingService {
    private final SafetyTrainingMapper mapper;

    public PageInfo<SafetyTraining> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(SafetyTraining entity) {
        if (entity.getId() == null) mapper.insert(entity);
        else mapper.update(entity);
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        mapper.updateStatus(id, status);
    }
}
