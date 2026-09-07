package com.aigccopyright.service;

import com.aigccopyright.common.BusinessException;
import com.aigccopyright.entity.ImageAsset;
import com.aigccopyright.mapper.ImageAssetMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class ImageAssetService extends ServiceImpl<ImageAssetMapper, ImageAsset> {

    public Page<ImageAsset> page(Integer pageNum, Integer pageSize, String keyword, String category, Integer status) {
        LambdaQueryWrapper<ImageAsset> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(ImageAsset::getTitle, keyword).or().like(ImageAsset::getPromptText, keyword).or().like(ImageAsset::getModelName, keyword));
        wrapper.eq(StringUtils.hasText(category), ImageAsset::getCategory, category);
        wrapper.eq(status != null, ImageAsset::getStatus, status);
        wrapper.orderByDesc(ImageAsset::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(ImageAsset entity, Long userId) {
        if (!StringUtils.hasText(entity.getTitle()) || !StringUtils.hasText(entity.getImageUrl())) {
            throw new BusinessException(400, "图片作品信息不完整");
        }
        if (entity.getId() == null) {
            entity.setCreatorId(userId);
            entity.setStatus(entity.getStatus() == null ? 0 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }
}
