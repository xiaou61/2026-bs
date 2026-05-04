package com.promptops.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.promptops.common.BusinessException;
import com.promptops.entity.PromptTestCase;
import com.promptops.mapper.PromptTestCaseMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class PromptTestCaseService extends ServiceImpl<PromptTestCaseMapper, PromptTestCase> {

    public Page<PromptTestCase> page(Integer pageNum, Integer pageSize, Long assetId, String difficulty, Integer status) {
        LambdaQueryWrapper<PromptTestCase> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(assetId != null, PromptTestCase::getAssetId, assetId);
        wrapper.eq(StringUtils.hasText(difficulty), PromptTestCase::getDifficulty, difficulty);
        wrapper.eq(status != null, PromptTestCase::getStatus, status);
        wrapper.orderByDesc(PromptTestCase::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(PromptTestCase entity, Long userId) {
        if (entity.getAssetId() == null || !StringUtils.hasText(entity.getTitle()) || !StringUtils.hasText(entity.getInputText())) {
            throw new BusinessException(400, "测试用例信息不完整");
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
