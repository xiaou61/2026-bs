package com.vehicleclaim.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vehicleclaim.entity.InsurancePolicy;
import com.vehicleclaim.mapper.InsurancePolicyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsurancePolicyService {
    private final InsurancePolicyMapper researchProjectMapper;

    public PageInfo<InsurancePolicy> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(researchProjectMapper.selectPage(keyword, status));
    }

    public void save(InsurancePolicy entity) {
        if (entity.getId() == null) researchProjectMapper.insert(entity);
        else researchProjectMapper.update(entity);
    }

    public void delete(Long id) {
        researchProjectMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        researchProjectMapper.updateStatus(id, status);
    }
}




