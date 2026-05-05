package com.researchfund.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.researchfund.entity.BudgetAllocation;
import com.researchfund.mapper.BudgetAllocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetAllocationService {
    private final BudgetAllocationMapper budgetAllocationMapper;

    public PageInfo<BudgetAllocation> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(budgetAllocationMapper.selectPage(keyword, status));
    }

    public void save(BudgetAllocation entity) {
        if (entity.getId() == null) budgetAllocationMapper.insert(entity);
        else budgetAllocationMapper.update(entity);
    }

    public void delete(Long id) {
        budgetAllocationMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        budgetAllocationMapper.updateStatus(id, status);
    }
}
