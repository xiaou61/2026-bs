package com.eldercare.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eldercare.entity.CaregiverProfile;
import com.eldercare.mapper.CaregiverProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaregiverProfileService {
    private final CaregiverProfileMapper budgetAllocationMapper;

    public PageInfo<CaregiverProfile> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(budgetAllocationMapper.selectPage(keyword, status));
    }

    public void save(CaregiverProfile entity) {
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







