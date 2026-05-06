package com.eldercare.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eldercare.entity.ElderProfile;
import com.eldercare.mapper.ElderProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElderProfileService {
    private final ElderProfileMapper budgetCategoryMapper;

    public PageInfo<ElderProfile> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(budgetCategoryMapper.selectPage(keyword, status));
    }

    public void save(ElderProfile entity) {
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







