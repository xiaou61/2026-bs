package com.xiaou.herbal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.herbal.common.Constants;
import com.xiaou.herbal.entity.CreatorAuth;
import com.xiaou.herbal.entity.User;
import com.xiaou.herbal.mapper.CreatorAuthMapper;
import com.xiaou.herbal.mapper.UserMapper;
import com.xiaou.herbal.service.CreatorAuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreatorAuthServiceImpl extends ServiceImpl<CreatorAuthMapper, CreatorAuth> implements CreatorAuthService {

    private final UserMapper userMapper;

    public CreatorAuthServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public void applyAuth(Long userId, String authType, String credentials) {
        LambdaQueryWrapper<CreatorAuth> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CreatorAuth::getUserId, userId);
        CreatorAuth existing = baseMapper.selectOne(wrapper);

        if (existing != null) {
            throw new RuntimeException("已有认证申请");
        }

        CreatorAuth auth = CreatorAuth.builder()
                .userId(userId)
                .authType(authType)
                .credentials(credentials)
                .status(Constants.CreatorAuthStatus.PENDING)
                .createTime(LocalDateTime.now())
                .build();

        baseMapper.insert(auth);
    }

    @Override
    public List<CreatorAuth> getPendingAuths() {
        LambdaQueryWrapper<CreatorAuth> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CreatorAuth::getStatus, Constants.CreatorAuthStatus.PENDING)
                .orderByDesc(CreatorAuth::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public void approveAuth(Long authId) {
        CreatorAuth auth = baseMapper.selectById(authId);
        auth.setStatus(Constants.CreatorAuthStatus.APPROVED);
        auth.setAuthTime(LocalDateTime.now());
        baseMapper.updateById(auth);

        User user = userMapper.selectById(auth.getUserId());
        user.setUserType(Constants.UserType.CREATOR);
        userMapper.updateById(user);
    }

    @Override
    @Transactional
    public void rejectAuth(Long authId) {
        CreatorAuth auth = baseMapper.selectById(authId);
        auth.setStatus(Constants.CreatorAuthStatus.REJECTED);
        baseMapper.updateById(auth);
    }

    @Override
    public CreatorAuth getUserAuth(Long userId) {
        LambdaQueryWrapper<CreatorAuth> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CreatorAuth::getUserId, userId);
        return baseMapper.selectOne(wrapper);
    }
}
