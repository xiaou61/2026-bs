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
    private final RoutePlanMapper routePlanMapper;

    public PageInfo<RoutePlan> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(routePlanMapper.selectPage(keyword, status));
    }

    public void save(RoutePlan entity) {
        if (entity.getId() == null) routePlanMapper.insert(entity);
        else routePlanMapper.update(entity);
    }

    public void delete(Long id) {
        routePlanMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        routePlanMapper.updateStatus(id, status);
    }
}

