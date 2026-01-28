package com.agriculture.service;

import com.agriculture.entity.Crop;
import com.agriculture.mapper.CropMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CropService extends ServiceImpl<CropMapper, Crop> {

    public Page<Crop> getCropPage(Integer pageNum, Integer pageSize, String name, Long categoryId) {
        Page<Crop> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Crop> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like(Crop::getName, name);
        }
        if (categoryId != null) {
            wrapper.eq(Crop::getCategoryId, categoryId);
        }
        wrapper.orderByDesc(Crop::getCreateTime);
        return this.page(page, wrapper);
    }
}
