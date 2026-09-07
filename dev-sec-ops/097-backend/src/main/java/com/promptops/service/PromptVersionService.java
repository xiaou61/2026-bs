package com.promptops.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.promptops.common.BusinessException;
import com.promptops.entity.PromptVersion;
import com.promptops.mapper.PromptVersionMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class PromptVersionService extends ServiceImpl<PromptVersionMapper, PromptVersion> {

    public Page<PromptVersion> page(Integer pageNum, Integer pageSize, Long assetId, Integer status) {
        LambdaQueryWrapper<PromptVersion> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(assetId != null, PromptVersion::getAssetId, assetId);
        wrapper.eq(status != null, PromptVersion::getStatus, status);
        wrapper.orderByDesc(PromptVersion::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(PromptVersion entity, Long userId) {
        if (entity.getAssetId() == null || !StringUtils.hasText(entity.getVersionNo()) || !StringUtils.hasText(entity.getContent())) {
            throw new BusinessException(400, "Prompt 版本信息不完整");
        }
        if (entity.getId() == null) {
            entity.setCreatorId(userId);
            entity.setStatus(entity.getStatus() == null ? 0 : entity.getStatus());
            entity.setIsBaseline(entity.getIsBaseline() == null ? 0 : entity.getIsBaseline());
            entity.setCreateTime(LocalDateTime.now());
        }
        if (entity.getIsBaseline() != null && entity.getIsBaseline() == 1) {
            update(new com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper<PromptVersion>()
                    .eq(PromptVersion::getAssetId, entity.getAssetId())
                    .set(PromptVersion::getIsBaseline, 0));
        }
        saveOrUpdate(entity);
    }

    public void publish(Long id) {
        PromptVersion version = getById(id);
        if (version == null) {
            throw new BusinessException(400, "版本不存在");
        }
        version.setStatus(1);
        updateById(version);
    }
}
