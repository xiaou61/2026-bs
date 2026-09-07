package com.aigccopyright.service;

import com.aigccopyright.common.BusinessException;
import com.aigccopyright.entity.AppealRecord;
import com.aigccopyright.mapper.AppealRecordMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class AppealRecordService extends ServiceImpl<AppealRecordMapper, AppealRecord> {

    public Page<AppealRecord> page(Integer pageNum, Integer pageSize, String targetType, Integer status, String keyword) {
        LambdaQueryWrapper<AppealRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(targetType), AppealRecord::getTargetType, targetType);
        wrapper.eq(status != null, AppealRecord::getStatus, status);
        wrapper.like(StringUtils.hasText(keyword), AppealRecord::getReason, keyword);
        wrapper.orderByDesc(AppealRecord::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(AppealRecord entity, Long userId) {
        if (!StringUtils.hasText(entity.getTargetType()) || entity.getTargetId() == null || !StringUtils.hasText(entity.getReason())) {
            throw new BusinessException(400, "申诉信息不完整");
        }
        if (entity.getId() == null) {
            entity.setUserId(userId);
            entity.setStatus(0);
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void handle(Long id, Integer status, String comment) {
        AppealRecord record = getById(id);
        if (record == null) {
            throw new BusinessException(400, "申诉记录不存在");
        }
        record.setStatus(status == null ? 1 : status);
        record.setHandleComment(comment);
        record.setUpdateTime(LocalDateTime.now());
        updateById(record);
    }
}
