package com.aigccopyright.service;

import com.aigccopyright.entity.RiskTag;
import com.aigccopyright.mapper.RiskTagMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class RiskTagService extends ServiceImpl<RiskTagMapper, RiskTag> {

    public Page<RiskTag> page(Integer pageNum, Integer pageSize, String keyword, String tagType, Integer status) {
        LambdaQueryWrapper<RiskTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(RiskTag::getTagName, keyword).or().like(RiskTag::getDescription, keyword));
        wrapper.eq(StringUtils.hasText(tagType), RiskTag::getTagType, tagType);
        wrapper.eq(status != null, RiskTag::getStatus, status);
        wrapper.orderByDesc(RiskTag::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(RiskTag entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }
}
