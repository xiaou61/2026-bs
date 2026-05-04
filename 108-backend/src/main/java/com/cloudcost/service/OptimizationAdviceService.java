package com.cloudcost.service;

import com.cloudcost.entity.OptimizationAdvice;
import com.cloudcost.mapper.OptimizationAdviceMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimizationAdviceService {
    private final OptimizationAdviceMapper mapper;

    public PageInfo<OptimizationAdvice> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(OptimizationAdvice entity) {
        if (entity.getId() == null) {
            mapper.insert(entity);
        } else {
            mapper.update(entity);
        }
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        mapper.updateStatus(id, status);
    }
}
