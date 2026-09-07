package com.aigccopyright.service;

import com.aigccopyright.entity.LicenseRecord;
import com.aigccopyright.mapper.LicenseRecordMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class LicenseRecordService extends ServiceImpl<LicenseRecordMapper, LicenseRecord> {

    public Page<LicenseRecord> page(Integer pageNum, Integer pageSize, Long registerId, Integer status, String keyword) {
        LambdaQueryWrapper<LicenseRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(registerId != null, LicenseRecord::getRegisterId, registerId);
        wrapper.eq(status != null, LicenseRecord::getStatus, status);
        wrapper.and(StringUtils.hasText(keyword), item -> item.like(LicenseRecord::getLicensee, keyword).or().like(LicenseRecord::getPurpose, keyword));
        wrapper.orderByDesc(LicenseRecord::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(LicenseRecord entity) {
        if (entity.getId() == null) {
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        saveOrUpdate(entity);
    }

    public void revoke(Long id) {
        LicenseRecord record = getById(id);
        if (record != null) {
            record.setStatus(2);
            record.setUpdateTime(LocalDateTime.now());
            updateById(record);
        }
    }
}
