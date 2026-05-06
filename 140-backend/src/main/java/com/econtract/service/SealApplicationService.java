package com.econtract.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.econtract.entity.SealApplication;
import com.econtract.mapper.SealApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SealApplicationService {
    private final SealApplicationMapper invoiceRecordMapper;

    public PageInfo<SealApplication> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(invoiceRecordMapper.selectPage(keyword, status));
    }

    public void save(SealApplication entity) {
        if (entity.getId() == null) invoiceRecordMapper.insert(entity);
        else invoiceRecordMapper.update(entity);
    }

    public void delete(Long id) {
        invoiceRecordMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        invoiceRecordMapper.updateStatus(id, status);
    }
}



