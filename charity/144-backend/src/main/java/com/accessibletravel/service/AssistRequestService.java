package com.accessibletravel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.accessibletravel.entity.AssistRequest;
import com.accessibletravel.mapper.AssistRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssistRequestService {
    private final AssistRequestMapper assistRequestMapper;

    public PageInfo<AssistRequest> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(assistRequestMapper.selectPage(keyword, status));
    }

    public void save(AssistRequest entity) {
        if (entity.getId() == null) assistRequestMapper.insert(entity);
        else assistRequestMapper.update(entity);
    }

    public void delete(Long id) {
        assistRequestMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        assistRequestMapper.updateStatus(id, status);
    }
}

