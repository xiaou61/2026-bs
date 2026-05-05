package com.greenhouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greenhouse.entity.EnvironmentReading;
import com.greenhouse.mapper.EnvironmentReadingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnvironmentReadingService {
    private final EnvironmentReadingMapper environmentReadingMapper;

    public PageInfo<EnvironmentReading> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(environmentReadingMapper.selectPage(keyword, status));
    }

    public void save(EnvironmentReading entity) {
        if (entity.getId() == null) environmentReadingMapper.insert(entity);
        else environmentReadingMapper.update(entity);
    }

    public void delete(Long id) {
        environmentReadingMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        environmentReadingMapper.updateStatus(id, status);
    }
}
