package com.servicequality.service;

import com.servicequality.common.BusinessException;
import com.servicequality.entity.KnowledgeArticle;
import com.servicequality.mapper.KnowledgeArticleMapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class KnowledgeArticleService extends ServiceImpl<KnowledgeArticleMapper, KnowledgeArticle> {
    public Page<KnowledgeArticle> page(Integer pageNum, Integer pageSize, String keyword, Long categoryId, Integer status) {
        LambdaQueryWrapper<KnowledgeArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(KnowledgeArticle::getTitle, keyword).or().like(KnowledgeArticle::getKeywords, keyword).or().like(KnowledgeArticle::getContent, keyword));
        wrapper.eq(categoryId != null, KnowledgeArticle::getCategoryId, categoryId);
        wrapper.eq(status != null, KnowledgeArticle::getStatus, status);
        wrapper.orderByDesc(KnowledgeArticle::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(KnowledgeArticle entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void publish(Long id) {
        KnowledgeArticle entity = getById(id);
        if (entity == null) {
            throw new BusinessException(400, "知识文章不存在");
        }
        entity.setStatus(1);
        entity.setUpdateTime(LocalDateTime.now());
        updateById(entity);
    }

    public void offline(Long id) {
        KnowledgeArticle entity = getById(id);
        if (entity == null) {
            throw new BusinessException(400, "知识文章不存在");
        }
        entity.setStatus(0);
        entity.setUpdateTime(LocalDateTime.now());
        updateById(entity);
    }

}
