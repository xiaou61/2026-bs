package com.greenhouse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.greenhouse.entity.FertilizerPlan;
import com.greenhouse.mapper.FertilizerPlanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FertilizerPlanService {
    private final FertilizerPlanMapper fertilizerPlanMapper;

    public PageInfo<FertilizerPlan> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(fertilizerPlanMapper.selectPage(keyword, status));
    }

    public void save(FertilizerPlan entity) {
        if (entity.getId() == null) fertilizerPlanMapper.insert(entity);
        else fertilizerPlanMapper.update(entity);
    }

    public void delete(Long id) {
        fertilizerPlanMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        fertilizerPlanMapper.updateStatus(id, status);
    }
}
