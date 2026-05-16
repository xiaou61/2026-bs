package com.p190.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.p190.entity.BizRecord03;
import com.p190.mapper.BizRecord03Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BizRecord03Service {
    private final BizRecord03Mapper mapper;

    public PageInfo<BizRecord03> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(BizRecord03 entity) {
        if (entity.getId() == null) mapper.insert(entity);
        else mapper.update(entity);
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        mapper.updateStatus(id, status);
    }
}
