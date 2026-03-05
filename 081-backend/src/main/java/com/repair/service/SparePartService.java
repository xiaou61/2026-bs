package com.repair.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.entity.SparePart;
import com.repair.mapper.SparePartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SparePartService {

    @Autowired
    private SparePartMapper sparePartMapper;

    public Page<SparePart> getList(int pageNum, int pageSize, String name, String brand, Integer status) {
        Page<SparePart> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SparePart> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrapper.like("name", name);
        }
        if (StringUtils.hasText(brand)) {
            wrapper.like("brand", brand);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return sparePartMapper.selectPage(page, wrapper);
    }

    public void add(SparePart part) {
        sparePartMapper.insert(part);
    }

    public void update(SparePart part) {
        sparePartMapper.updateById(part);
    }

    public void delete(Long id) {
        sparePartMapper.deleteById(id);
    }
}
