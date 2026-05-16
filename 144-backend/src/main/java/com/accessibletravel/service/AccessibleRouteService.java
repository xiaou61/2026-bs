package com.accessibletravel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.accessibletravel.entity.AccessibleRoute;
import com.accessibletravel.mapper.AccessibleRouteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessibleRouteService {
    private final AccessibleRouteMapper accessibleRouteMapper;

    public PageInfo<AccessibleRoute> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(accessibleRouteMapper.selectPage(keyword, status));
    }

    public void save(AccessibleRoute entity) {
        if (entity.getId() == null) accessibleRouteMapper.insert(entity);
        else accessibleRouteMapper.update(entity);
    }

    public void delete(Long id) {
        accessibleRouteMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        accessibleRouteMapper.updateStatus(id, status);
    }
}

