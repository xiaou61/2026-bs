package com.accessibletravel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.accessibletravel.entity.RoutePlan;
import com.accessibletravel.mapper.RoutePlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoutePlanService {
    private final RoutePlanMapper approvalTaskMapper;

    public PageInfo<RoutePlan> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(approvalTaskMapper.selectPage(keyword, status));
    }

    public void save(RoutePlan entity) {
        if (entity.getId() == null) approvalTaskMapper.insert(entity);
        else approvalTaskMapper.update(entity);
    }

    public void delete(Long id) {
        approvalTaskMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        approvalTaskMapper.updateStatus(id, status);
    }
}





