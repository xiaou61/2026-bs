package com.aigccopyright.service;

import com.aigccopyright.common.BusinessException;
import com.aigccopyright.entity.InfringementClue;
import com.aigccopyright.mapper.InfringementClueMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class InfringementClueService extends ServiceImpl<InfringementClueMapper, InfringementClue> {

    public Page<InfringementClue> page(Integer pageNum, Integer pageSize, Long assetId, Integer status, String keyword) {
        LambdaQueryWrapper<InfringementClue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(assetId != null, InfringementClue::getAssetId, assetId);
        wrapper.eq(status != null, InfringementClue::getStatus, status);
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(InfringementClue::getPlatformName, keyword).or().like(InfringementClue::getDescription, keyword));
        wrapper.orderByDesc(InfringementClue::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(InfringementClue entity, Long userId) {
        if (entity.getAssetId() == null || !StringUtils.hasText(entity.getClueUrl())) {
            throw new BusinessException(400, "侵权线索信息不完整");
        }
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 0 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void handle(Long id, Long handlerId, Integer status, String comment) {
        InfringementClue clue = getById(id);
        if (clue == null) {
            throw new BusinessException(400, "侵权线索不存在");
        }
        clue.setStatus(status == null ? 1 : status);
        clue.setHandlerId(handlerId);
        clue.setHandleComment(comment);
        clue.setUpdateTime(LocalDateTime.now());
        updateById(clue);
    }
}
