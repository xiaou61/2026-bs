package com.accessibletravel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.accessibletravel.entity.FacilityPoint;
import com.accessibletravel.mapper.FacilityPointMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilityPointService {
    private final FacilityPointMapper facilityPointMapper;

    public PageInfo<FacilityPoint> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(facilityPointMapper.selectPage(keyword, status));
    }

    public void save(FacilityPoint entity) {
        if (entity.getId() == null) facilityPointMapper.insert(entity);
        else facilityPointMapper.update(entity);
    }

    public void delete(Long id) {
        facilityPointMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        facilityPointMapper.updateStatus(id, status);
    }
}

