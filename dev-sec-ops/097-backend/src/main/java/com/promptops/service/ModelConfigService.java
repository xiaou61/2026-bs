package com.promptops.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.promptops.entity.ModelConfig;
import com.promptops.mapper.ModelConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class ModelConfigService extends ServiceImpl<ModelConfigMapper, ModelConfig> {

    public Page<ModelConfig> page(Integer pageNum, Integer pageSize, String keyword, Integer status) {
        LambdaQueryWrapper<ModelConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), w -> w.like(ModelConfig::getName, keyword).or().like(ModelConfig::getProvider, keyword).or().like(ModelConfig::getModelName, keyword));
        wrapper.eq(status != null, ModelConfig::getStatus, status);
        wrapper.orderByDesc(ModelConfig::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(ModelConfig entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setIsDefault(entity.getIsDefault() == null ? 0 : entity.getIsDefault());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        if (entity.getIsDefault() != null && entity.getIsDefault() == 1) {
            update(new LambdaUpdateWrapper<ModelConfig>().set(ModelConfig::getIsDefault, 0));
        }
        saveOrUpdate(entity);
    }
}
