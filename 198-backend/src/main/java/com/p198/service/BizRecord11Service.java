package com.p198.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.p198.entity.BizRecord11;
import com.p198.mapper.BizRecord11Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BizRecord11Service {
    private final BizRecord11Mapper mapper;

    public PageInfo<BizRecord11> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(BizRecord11 entity) {
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
