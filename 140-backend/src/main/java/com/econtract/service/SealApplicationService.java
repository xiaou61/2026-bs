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
    private final SealApplicationMapper sealApplicationMapper;

    public PageInfo<SealApplication> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(sealApplicationMapper.selectPage(keyword, status));
    }

    public void save(SealApplication entity) {
        if (entity.getId() == null) sealApplicationMapper.insert(entity);
        else sealApplicationMapper.update(entity);
    }

    public void delete(Long id) {
        sealApplicationMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        sealApplicationMapper.updateStatus(id, status);
    }
}



