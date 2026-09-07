package com.xiaou.collabboard.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.collabboard.entity.Template;
import com.xiaou.collabboard.mapper.TemplateMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TemplateService extends ServiceImpl<TemplateMapper, Template> {

    public Template createTemplate(Long userId, Long teamId, String templateName, String templateType, String category, String content) {
        Template template = new Template();
        template.setUserId(userId);
        template.setTeamId(teamId != null ? teamId : 0L);
        template.setTemplateName(templateName);
        template.setTemplateType(templateType);
        template.setCategory(category);
        template.setContent(content);
        template.setUseCount(0);
        template.setCollectCount(0);
        template.setRating(BigDecimal.ZERO);
        template.setIsPublic(0);
        template.setIsOfficial(0);
        template.setStatus(1);
        template.setCreateTime(LocalDateTime.now());
        template.setUpdateTime(LocalDateTime.now());

        baseMapper.insert(template);
        return template;
    }

    public IPage<Template> getTemplateList(String templateType, String category, Integer isPublic, Integer pageNum, Integer pageSize) {
        Page<Template> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Template> wrapper = new LambdaQueryWrapper<>();
        
        wrapper.eq(Template::getStatus, 1);

        if (templateType != null) {
            wrapper.eq(Template::getTemplateType, templateType);
        }

        if (category != null) {
            wrapper.eq(Template::getCategory, category);
        }

        if (isPublic != null) {
            wrapper.eq(Template::getIsPublic, isPublic);
        }

        wrapper.orderByDesc(Template::getUseCount)
                .orderByDesc(Template::getCreateTime);

        return baseMapper.selectPage(page, wrapper);
    }

    public void incrementUseCount(Long templateId) {
        Template template = baseMapper.selectById(templateId);
        if (template != null) {
            template.setUseCount(template.getUseCount() + 1);
            baseMapper.updateById(template);
        }
    }

    public void incrementCollectCount(Long templateId) {
        Template template = baseMapper.selectById(templateId);
        if (template != null) {
            template.setCollectCount(template.getCollectCount() + 1);
            baseMapper.updateById(template);
        }
    }

    public void decrementCollectCount(Long templateId) {
        Template template = baseMapper.selectById(templateId);
        if (template != null && template.getCollectCount() > 0) {
            template.setCollectCount(template.getCollectCount() - 1);
            baseMapper.updateById(template);
        }
    }
}

