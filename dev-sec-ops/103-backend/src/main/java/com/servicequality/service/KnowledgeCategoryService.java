package com.servicequality.service;

import com.servicequality.common.BusinessException;
import com.servicequality.entity.KnowledgeCategory;
import com.servicequality.mapper.KnowledgeCategoryMapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class KnowledgeCategoryService extends ServiceImpl<KnowledgeCategoryMapper, KnowledgeCategory> {
    public Page<KnowledgeCategory> page(Integer pageNum, Integer pageSize, String keyword, Integer status) {
        LambdaQueryWrapper<KnowledgeCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(KnowledgeCategory::getCategoryName, keyword).or().like(KnowledgeCategory::getParentName, keyword));
        wrapper.eq(status != null, KnowledgeCategory::getStatus, status);
        wrapper.orderByDesc(KnowledgeCategory::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(KnowledgeCategory entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

}
