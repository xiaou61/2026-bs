package com.xiaou.campusclub.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.campusclub.dto.LoginRequest;
import com.xiaou.campusclub.dto.RegisterRequest;
import com.xiaou.campusclub.entity.*;
import com.xiaou.campusclub.exception.BusinessException;
import com.xiaou.campusclub.mapper.*;
import com.xiaou.campusclub.service.UserService;
import com.xiaou.campusclub.util.JwtUtil;
import com.xiaou.campusclub.util.PasswordUtil;
import com.xiaou.campusclub.vo.LoginVO;
import com.xiaou.campusclub.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserInterestMapper userInterestMapper;
    
    @Autowired
    private InterestTagMapper interestTagMapper;
    
    @Autowired
    private PointsRecordMapper pointsRecordMapper;
    
    @Autowired
    private UserBadgeMapper userBadgeMapper;
    
    @Autowired
    private BadgeMapper badgeMapper;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    @Transactional
    public LoginVO register(RegisterRequest request) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, request.getUsername());
        if (userMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("用户名已存在");
        }
        
        if (request.getStudentId() != null) {
            wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getStudentId, request.getStudentId());
            if (userMapper.selectCount(wrapper) > 0) {
                throw new BusinessException("学号已被注册");
            }
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(PasswordUtil.encode(request.getPassword()));
        user.setStudentId(request.getStudentId());
        user.setRealName(request.getRealName());
        user.setEmail(request.getEmail());
        user.setMajor(request.getMajor());
        user.setGrade(request.getGrade());
        user.setPoints(10);
        user.setLevel(1);
        user.setStatus(0);
        user.setCreateTime(LocalDateTime.now());
        
        userMapper.insert(user);
        
        addPoints(user.getId(), 10, "REGISTER", "注册账户");
        
        LambdaQueryWrapper<Badge> badgeWrapper = new LambdaQueryWrapper<>();
        badgeWrapper.eq(Badge::getConditionType, "REGISTER");
        Badge badge = badgeMapper.selectOne(badgeWrapper);
        if (badge != null) {
            UserBadge userBadge = new UserBadge();
            userBadge.setUserId(user.getId());
            userBadge.setBadgeId(badge.getId());
            userBadge.setObtainTime(LocalDateTime.now());
            userBadgeMapper.insert(userBadge);
        }
        
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), "user");
        
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setUserId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setRealName(user.getRealName());
        loginVO.setAvatar(user.getAvatar());
        
        return loginVO;
    }
    
    @Override
    public LoginVO login(LoginRequest request) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, request.getUsername());
        User user = userMapper.selectOne(wrapper);
        
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        if (!PasswordUtil.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        
        if (user.getStatus() == 1) {
            throw new BusinessException("账户已被禁用");
        }
        
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), "user");
        
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setUserId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setRealName(user.getRealName());
        loginVO.setAvatar(user.getAvatar());
        
        return loginVO;
    }
    
    @Override
    public UserInfoVO getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        UserInfoVO vo = BeanUtil.copyProperties(user, UserInfoVO.class);
        vo.setInterests(getUserInterests(userId));
        
        return vo;
    }
    
    @Override
    @Transactional
    public void updateProfile(Long userId, User user) {
        User existUser = userMapper.selectById(userId);
        if (existUser == null) {
            throw new BusinessException("用户不存在");
        }
        
        user.setId(userId);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }
    
    @Override
    @Transactional
    public void addPoints(Long userId, Integer points, String type, String description) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setPoints(user.getPoints() + points);
            
            if (user.getPoints() >= 1000) {
                user.setLevel(5);
            } else if (user.getPoints() >= 500) {
                user.setLevel(4);
            } else if (user.getPoints() >= 200) {
                user.setLevel(3);
            } else if (user.getPoints() >= 50) {
                user.setLevel(2);
            }
            
            userMapper.updateById(user);
            
            PointsRecord record = new PointsRecord();
            record.setUserId(userId);
            record.setPoints(points);
            record.setType(type);
            record.setDescription(description);
            record.setCreateTime(LocalDateTime.now());
            pointsRecordMapper.insert(record);
        }
    }
    
    @Override
    public List<String> getUserInterests(Long userId) {
        LambdaQueryWrapper<UserInterest> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInterest::getUserId, userId);
        List<UserInterest> userInterests = userInterestMapper.selectList(wrapper);
        
        return userInterests.stream()
                .map(ui -> {
                    InterestTag tag = interestTagMapper.selectById(ui.getInterestId());
                    return tag != null ? tag.getName() : null;
                })
                .filter(name -> name != null)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public void addUserInterest(Long userId, Long interestId) {
        LambdaQueryWrapper<UserInterest> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInterest::getUserId, userId)
               .eq(UserInterest::getInterestId, interestId);
        if (userInterestMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("已添加该兴趣标签");
        }
        
        UserInterest userInterest = new UserInterest();
        userInterest.setUserId(userId);
        userInterest.setInterestId(interestId);
        userInterest.setCreateTime(LocalDateTime.now());
        userInterestMapper.insert(userInterest);
        
        InterestTag tag = interestTagMapper.selectById(interestId);
        if (tag != null) {
            tag.setUserCount(tag.getUserCount() + 1);
            interestTagMapper.updateById(tag);
        }
    }
    
    @Override
    @Transactional
    public void removeUserInterest(Long userId, Long interestId) {
        LambdaQueryWrapper<UserInterest> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInterest::getUserId, userId)
               .eq(UserInterest::getInterestId, interestId);
        userInterestMapper.delete(wrapper);
        
        InterestTag tag = interestTagMapper.selectById(interestId);
        if (tag != null && tag.getUserCount() > 0) {
            tag.setUserCount(tag.getUserCount() - 1);
            interestTagMapper.updateById(tag);
        }
    }
}

