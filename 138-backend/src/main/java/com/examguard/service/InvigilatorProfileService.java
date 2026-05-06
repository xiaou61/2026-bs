package com.examguard.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.examguard.entity.InvigilatorProfile;
import com.examguard.mapper.InvigilatorProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvigilatorProfileService {
    private final InvigilatorProfileMapper budgetCategoryMapper;

    public PageInfo<InvigilatorProfile> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(budgetCategoryMapper.selectPage(keyword, status));
    }

    public void save(InvigilatorProfile entity) {
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


