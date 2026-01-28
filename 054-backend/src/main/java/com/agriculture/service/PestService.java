package com.agriculture.service;

import com.agriculture.entity.PestDisease;
import com.agriculture.mapper.PestDiseaseMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PestService extends ServiceImpl<PestDiseaseMapper, PestDisease> {

    public Page<PestDisease> getPage(Integer pageNum, Integer pageSize, String name, Integer type) {
        Page<PestDisease> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<PestDisease> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like(PestDisease::getName, name);
        }
        if (type != null) {
            wrapper.eq(PestDisease::getType, type);
        }
        wrapper.orderByDesc(PestDisease::getCreateTime);
        return this.page(page, wrapper);
    }
}
