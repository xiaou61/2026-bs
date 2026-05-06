package com.econtract.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.econtract.entity.CounterpartyProfile;
import com.econtract.mapper.CounterpartyProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounterpartyProfileService {
    private final CounterpartyProfileMapper budgetCategoryMapper;

    public PageInfo<CounterpartyProfile> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(budgetCategoryMapper.selectPage(keyword, status));
    }

    public void save(CounterpartyProfile entity) {
        if (entity.getId() == null) budgetCategoryMapper.insert(entity);
        else budgetCategoryMapper.update(entity);
    }

    public void delete(Long id) {
        budgetCategoryMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        budgetCategoryMapper.updateStatus(id, status);
    }
}



