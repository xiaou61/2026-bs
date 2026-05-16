package com.logisticspark.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.logisticspark.entity.VehicleProfile;
import com.logisticspark.mapper.VehicleProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleProfileService {
    private final VehicleProfileMapper mapper;

    public PageInfo<VehicleProfile> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(VehicleProfile entity) {
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
