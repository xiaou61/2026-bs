package com.accessibletravel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.accessibletravel.entity.AssistRequest;
import com.accessibletravel.mapper.AssistRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssistRequestService {
    private final AssistRequestMapper expenseClaimMapper;

    public PageInfo<AssistRequest> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(expenseClaimMapper.selectPage(keyword, status));
    }

    public void save(AssistRequest entity) {
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





