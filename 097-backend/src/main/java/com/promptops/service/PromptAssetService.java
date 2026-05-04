package com.promptops.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.promptops.common.BusinessException;
import com.promptops.entity.PromptAsset;
import com.promptops.mapper.PromptAssetMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class PromptAssetService extends ServiceImpl<PromptAssetMapper, PromptAsset> {

    public Page<PromptAsset> page(Integer pageNum, Integer pageSize, String keyword, Long categoryId, Integer status) {
        LambdaQueryWrapper<PromptAsset> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), w -> w.like(PromptAsset::getTitle, keyword).or().like(PromptAsset::getScene, keyword).or().like(PromptAsset::getTags, keyword));
        wrapper.eq(categoryId != null, PromptAsset::getCategoryId, categoryId);
        wrapper.eq(status != null, PromptAsset::getStatus, status);
        wrapper.orderByDesc(PromptAsset::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(PromptAsset entity, Long userId) {
        if (!StringUtils.hasText(entity.getTitle()) || entity.getCategoryId() == null || entity.getTeamId() == null) {
            throw new BusinessException(400, "Prompt 资产信息不完整");
        }
        if (entity.getId() == null) {
            entity.setCreatorId(userId);
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }
}
