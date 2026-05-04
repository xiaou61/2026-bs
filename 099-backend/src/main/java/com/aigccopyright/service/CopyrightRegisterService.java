package com.aigccopyright.service;

import com.aigccopyright.common.BusinessException;
import com.aigccopyright.entity.CopyrightRegister;
import com.aigccopyright.mapper.CopyrightRegisterMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class CopyrightRegisterService extends ServiceImpl<CopyrightRegisterMapper, CopyrightRegister> {

    public Page<CopyrightRegister> page(Integer pageNum, Integer pageSize, Long assetId, Integer registerStatus) {
        LambdaQueryWrapper<CopyrightRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(assetId != null, CopyrightRegister::getAssetId, assetId);
        wrapper.eq(registerStatus != null, CopyrightRegister::getRegisterStatus, registerStatus);
        wrapper.orderByDesc(CopyrightRegister::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(CopyrightRegister entity) {
        if (entity.getAssetId() == null || !StringUtils.hasText(entity.getAuthorName())) {
            throw new BusinessException(400, "版权登记信息不完整");
        }
        if (entity.getId() == null) {
            entity.setRegisterStatus(entity.getRegisterStatus() == null ? 0 : entity.getRegisterStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void approve(Long id, String comment) {
        CopyrightRegister register = getById(id);
        if (register == null) {
            throw new BusinessException(400, "版权登记不存在");
        }
        register.setRegisterStatus(1);
        register.setReviewComment(comment);
        register.setUpdateTime(LocalDateTime.now());
        updateById(register);
    }

    public void reject(Long id, String comment) {
        CopyrightRegister register = getById(id);
        if (register == null) {
            throw new BusinessException(400, "版权登记不存在");
        }
        register.setRegisterStatus(2);
        register.setReviewComment(comment);
        register.setUpdateTime(LocalDateTime.now());
        updateById(register);
    }
}
