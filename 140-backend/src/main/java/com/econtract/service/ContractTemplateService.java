package com.econtract.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.econtract.entity.ContractTemplate;
import com.econtract.mapper.ContractTemplateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractTemplateService {
    private final ContractTemplateMapper contractTemplateMapper;

    public PageInfo<ContractTemplate> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(contractTemplateMapper.selectPage(keyword, status));
    }

    public void save(ContractTemplate entity) {
        if (entity.getId() == null) contractTemplateMapper.insert(entity);
        else contractTemplateMapper.update(entity);
    }

    public void delete(Long id) {
        contractTemplateMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        contractTemplateMapper.updateStatus(id, status);
    }
}



