package com.greenhouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greenhouse.entity.EnvironmentSensor;
import com.greenhouse.mapper.EnvironmentSensorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnvironmentSensorService {
    private final EnvironmentSensorMapper environmentSensorMapper;

    public PageInfo<EnvironmentSensor> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(environmentSensorMapper.selectPage(keyword, status));
    }

    public void save(EnvironmentSensor entity) {
        if (entity.getId() == null) environmentSensorMapper.insert(entity);
        else environmentSensorMapper.update(entity);
    }

    public void delete(Long id) {
        environmentSensorMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        environmentSensorMapper.updateStatus(id, status);
    }
}
