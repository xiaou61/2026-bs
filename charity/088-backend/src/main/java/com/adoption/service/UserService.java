package com.adoption.service;

import com.adoption.common.BusinessException;
import com.adoption.entity.AdopterProfile;
import com.adoption.entity.SysUser;
import com.adoption.mapper.AdopterProfileMapper;
import com.adoption.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private AdopterProfileMapper adopterProfileMapper;

    public AdopterProfile getProfile(Long userId) {
        AdopterProfile profile = adopterProfileMapper.selectOne(Wrappers.<AdopterProfile>lambdaQuery().eq(AdopterProfile::getUserId, userId));
        if (profile == null) {
            profile = new AdopterProfile();
            profile.setUserId(userId);
            profile.setStatus(1);
            adopterProfileMapper.insert(profile);
        }
        SysUser user = sysUserMapper.selectById(userId);
        if (user != null) {
            profile.setRealName(user.getRealName());
            profile.setPhone(user.getPhone());
        }
        return profile;
    }

    public void updateProfile(Long userId, AdopterProfile profile) {
        if (!StringUtils.hasText(profile.getAddress()) || !StringUtils.hasText(profile.getAdoptionReason())) {
            throw new BusinessException("请完善住址和收养意愿");
        }
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (StringUtils.hasText(profile.getRealName())) {
            user.setRealName(profile.getRealName());
        }
        if (StringUtils.hasText(profile.getPhone())) {
            user.setPhone(profile.getPhone());
        }
        sysUserMapper.updateById(user);
        AdopterProfile dbProfile = adopterProfileMapper.selectOne(Wrappers.<AdopterProfile>lambdaQuery().eq(AdopterProfile::getUserId, userId));
        if (dbProfile == null) {
            profile.setUserId(userId);
            profile.setStatus(1);
            adopterProfileMapper.insert(profile);
            return;
        }
        profile.setId(dbProfile.getId());
        profile.setUserId(userId);
        adopterProfileMapper.updateById(profile);
    }
}
