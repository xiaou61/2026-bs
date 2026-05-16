package com.p195.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.p195.entity.BizRecord10;
import com.p195.mapper.BizRecord10Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BizRecord10Service {
    private final BizRecord10Mapper mapper;

    public PageInfo<BizRecord10> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(BizRecord10 entity) {
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
