package com.homeenergy.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homeenergy.entity.EnergyReading;
import com.homeenergy.mapper.EnergyReadingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnergyReadingService {
    private final EnergyReadingMapper mapper;

    public PageInfo<EnergyReading> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(EnergyReading entity) {
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
