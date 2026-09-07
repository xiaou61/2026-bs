package com.econtract.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.econtract.entity.ContractDraft;
import com.econtract.mapper.ContractDraftMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractDraftService {
    private final ContractDraftMapper contractDraftMapper;

    public PageInfo<ContractDraft> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(contractDraftMapper.selectPage(keyword, status));
    }

    public void save(ContractDraft entity) {
        if (entity.getId() == null) contractDraftMapper.insert(entity);
        else contractDraftMapper.update(entity);
    }

    public void delete(Long id) {
        contractDraftMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        contractDraftMapper.updateStatus(id, status);
    }
}



