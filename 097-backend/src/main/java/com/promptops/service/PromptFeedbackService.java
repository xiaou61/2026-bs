package com.promptops.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.promptops.common.BusinessException;
import com.promptops.entity.PromptFeedback;
import com.promptops.mapper.PromptFeedbackMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class PromptFeedbackService extends ServiceImpl<PromptFeedbackMapper, PromptFeedback> {

    public Page<PromptFeedback> page(Integer pageNum, Integer pageSize, Long assetId, Integer status, String priority) {
        LambdaQueryWrapper<PromptFeedback> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(assetId != null, PromptFeedback::getAssetId, assetId);
        wrapper.eq(status != null, PromptFeedback::getStatus, status);
        wrapper.eq(StringUtils.hasText(priority), PromptFeedback::getPriority, priority);
        wrapper.orderByDesc(PromptFeedback::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(PromptFeedback entity, Long userId) {
        if (entity.getAssetId() == null || !StringUtils.hasText(entity.getContent())) {
            throw new BusinessException(400, "反馈信息不完整");
        }
        if (entity.getId() == null) {
            entity.setUserId(userId);
            entity.setStatus(0);
            entity.setPriority(StringUtils.hasText(entity.getPriority()) ? entity.getPriority() : "中");
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void reply(Long id, Integer status, String replyContent) {
        PromptFeedback feedback = getById(id);
        if (feedback == null) {
            throw new BusinessException(400, "反馈不存在");
        }
        feedback.setStatus(status == null ? 1 : status);
        feedback.setReplyContent(replyContent);
        feedback.setUpdateTime(LocalDateTime.now());
        updateById(feedback);
    }
}
