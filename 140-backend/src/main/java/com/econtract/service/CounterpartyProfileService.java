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
    private final CounterpartyProfileMapper counterpartyProfileMapper;

    public PageInfo<CounterpartyProfile> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(counterpartyProfileMapper.selectPage(keyword, status));
    }

    public void save(CounterpartyProfile entity) {
        if (entity.getId() == null) counterpartyProfileMapper.insert(entity);
        else counterpartyProfileMapper.update(entity);
    }

    public void delete(Long id) {
        counterpartyProfileMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        counterpartyProfileMapper.updateStatus(id, status);
    }
}



