package com.accessibletravel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.accessibletravel.entity.AssistTask;
import com.accessibletravel.mapper.AssistTaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssistTaskService {
    private final AssistTaskMapper assistTaskMapper;

    public PageInfo<AssistTask> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(assistTaskMapper.selectPage(keyword, status));
    }

    public void save(AssistTask entity) {
        if (entity.getId() == null) assistTaskMapper.insert(entity);
        else assistTaskMapper.update(entity);
    }

    public void delete(Long id) {
        assistTaskMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        assistTaskMapper.updateStatus(id, status);
    }
}

