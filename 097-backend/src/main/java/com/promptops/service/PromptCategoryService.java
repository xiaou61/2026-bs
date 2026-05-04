package com.promptops.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.promptops.entity.PromptCategory;
import com.promptops.mapper.PromptCategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class PromptCategoryService extends ServiceImpl<PromptCategoryMapper, PromptCategory> {

    public Page<PromptCategory> page(Integer pageNum, Integer pageSize, String keyword, Integer status) {
        LambdaQueryWrapper<PromptCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), w -> w.like(PromptCategory::getName, keyword).or().like(PromptCategory::getCode, keyword));
        wrapper.eq(status != null, PromptCategory::getStatus, status);
        wrapper.orderByDesc(PromptCategory::getSort).orderByDesc(PromptCategory::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(PromptCategory entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setSort(entity.getSort() == null ? 0 : entity.getSort());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }
}
