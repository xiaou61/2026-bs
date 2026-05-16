package com.p194.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.p194.entity.BizRecord02;
import com.p194.mapper.BizRecord02Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BizRecord02Service {
    private final BizRecord02Mapper mapper;

    public PageInfo<BizRecord02> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(BizRecord02 entity) {
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
