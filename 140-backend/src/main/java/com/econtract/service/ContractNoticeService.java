package com.econtract.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.econtract.entity.ContractNotice;
import com.econtract.mapper.ContractNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractNoticeService {
    private final ContractNoticeMapper contractNoticeMapper;

    public PageInfo<ContractNotice> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(contractNoticeMapper.selectPage(keyword, status));
    }

    public void save(ContractNotice entity) {
        if (entity.getId() == null) contractNoticeMapper.insert(entity);
        else contractNoticeMapper.update(entity);
    }

    public void delete(Long id) {
        contractNoticeMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        contractNoticeMapper.updateStatus(id, status);
    }
}



