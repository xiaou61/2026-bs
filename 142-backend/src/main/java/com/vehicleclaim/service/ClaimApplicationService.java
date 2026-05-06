package com.vehicleclaim.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vehicleclaim.entity.ClaimApplication;
import com.vehicleclaim.mapper.ClaimApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClaimApplicationService {
    private final ClaimApplicationMapper expenseClaimMapper;

    public PageInfo<ClaimApplication> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(expenseClaimMapper.selectPage(keyword, status));
    }

    public void save(ClaimApplication entity) {
        if (entity.getId() == null) expenseClaimMapper.insert(entity);
        else expenseClaimMapper.update(entity);
    }

    public void delete(Long id) {
        expenseClaimMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        expenseClaimMapper.updateStatus(id, status);
    }
}




