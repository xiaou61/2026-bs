package com.xiaou.herbal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.herbal.entity.HealthProfile;
import com.xiaou.herbal.mapper.HealthProfileMapper;
import com.xiaou.herbal.service.HealthProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class HealthProfileServiceImpl extends ServiceImpl<HealthProfileMapper, HealthProfile> implements HealthProfileService {

    @Override
    public HealthProfile getUserProfile(Long userId) {
        LambdaQueryWrapper<HealthProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HealthProfile::getUserId, userId);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    @Transactional
    public void saveOrUpdateProfile(Long userId, HealthProfile profile) {
        HealthProfile existing = getUserProfile(userId);
        
        if (existing != null) {
            profile.setId(existing.getId());
            profile.setUserId(userId);
            profile.setCreateTime(existing.getCreateTime());
            profile.setUpdateTime(LocalDateTime.now());
            baseMapper.updateById(profile);
        } else {
            profile.setUserId(userId);
            profile.setCreateTime(LocalDateTime.now());
            profile.setUpdateTime(LocalDateTime.now());
            baseMapper.insert(profile);
        }
    }
}
