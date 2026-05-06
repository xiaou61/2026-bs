package com.examguard.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.examguard.entity.ViolationAppeal;
import com.examguard.mapper.ViolationAppealMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViolationAppealService {
    private final ViolationAppealMapper performanceStatisticMapper;

    public PageInfo<ViolationAppeal> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(performanceStatisticMapper.selectPage(keyword, status));
    }

    public void save(ViolationAppeal entity) {
        if (entity.getId() == null) performanceStatisticMapper.insert(entity);
        else performanceStatisticMapper.update(entity);
    }

    public void delete(Long id) {
        performanceStatisticMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        performanceStatisticMapper.updateStatus(id, status);
    }
}


