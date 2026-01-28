package com.agriculture.service;

import com.agriculture.entity.PestWarning;
import com.agriculture.mapper.PestWarningMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class WarningService extends ServiceImpl<PestWarningMapper, PestWarning> {

    public Page<PestWarning> getPage(Integer pageNum, Integer pageSize, String region, Integer warningLevel) {
        Page<PestWarning> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<PestWarning> wrapper = new LambdaQueryWrapper<>();
        if (region != null && !region.isEmpty()) {
            wrapper.like(PestWarning::getRegion, region);
        }
        if (warningLevel != null) {
            wrapper.eq(PestWarning::getWarningLevel, warningLevel);
        }
        wrapper.orderByDesc(PestWarning::getWarningDate);
        return this.page(page, wrapper);
    }
}
