package com.xiaou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.dto.UserLoginDTO;
import com.xiaou.dto.UserRegisterDTO;
import com.xiaou.dto.UserUpdateDTO;
import com.xiaou.entity.User;
import com.xiaou.exception.BusinessException;
import com.xiaou.mapper.UserMapper;
import com.xiaou.service.UserService;
import com.xiaou.utils.JwtUtil;
import com.xiaou.vo.UserInfoVO;
import com.xiaou.vo.UserLoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 64;

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String register(UserRegisterDTO registerDTO) {
        if (registerDTO == null || !StringUtils.hasText(registerDTO.getUsername()) || !StringUtils.hasText(registerDTO.getPassword())) {
            throw new BusinessException("用户名和密码不能为空");
        }
        validatePassword(registerDTO.getPassword());

        LambdaQueryWrapper<User> usernameWrapper = new LambdaQueryWrapper<>();
        usernameWrapper.eq(User::getUsername, registerDTO.getUsername());
        if (userMapper.selectOne(usernameWrapper) != null) {
            throw new BusinessException("用户名已存在");
        }

        LambdaQueryWrapper<User> studentIdWrapper = new LambdaQueryWrapper<>();
        studentIdWrapper.eq(User::getStudentId, registerDTO.getStudentId());
        if (userMapper.selectOne(studentIdWrapper) != null) {
            throw new BusinessException("学号已存在");
        }

        User user = new User();
        BeanUtil.copyProperties(registerDTO, user);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setCreditScore(100);
        user.setStatus(0);

        userMapper.insert(user);
        return "注册成功";
    }

    @Override
    public UserLoginVO login(UserLoginDTO loginDTO) {
        if (loginDTO == null || !StringUtils.hasText(loginDTO.getUsername()) || !StringUtils.hasText(loginDTO.getPassword())) {
            throw new BusinessException("用户名和密码不能为空");
        }

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, loginDTO.getUsername());
        User user = userMapper.selectOne(wrapper);

        if (user == null || !passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        if (user.getStatus() == 1) {
            throw new BusinessException("用户已被禁用");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getId());

        UserLoginVO loginVO = new UserLoginVO();
        loginVO.setToken(token);
        loginVO.setUserId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setRealName(user.getRealName());
        loginVO.setAvatar(user.getAvatar());
        loginVO.setCreditScore(user.getCreditScore());

        return loginVO;
    }

    @Override
    public UserInfoVO getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtil.copyProperties(user, userInfoVO);
        return userInfoVO;
    }

    @Override
    @Transactional
    public void updateUser(Long userId, UserUpdateDTO updateDTO) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        if (!user.getUsername().equals(updateDTO.getUsername())) {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUsername, updateDTO.getUsername())
                   .ne(User::getId, userId);
            if (userMapper.selectOne(wrapper) != null) {
                throw new BusinessException("用户名已存在");
            }
        }

        BeanUtil.copyProperties(updateDTO, user, "id", "password", "creditScore", "createTime");
        if (StringUtils.hasText(updateDTO.getPassword())) {
            validatePassword(updateDTO.getPassword());
            user.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }

        userMapper.updateById(user);
    }

    @Override
    public UserInfoVO getUserProfile(Long profileId) {
        User user = userMapper.selectById(profileId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(user.getId());
        userInfoVO.setUsername(user.getUsername());
        userInfoVO.setRealName(user.getRealName());
        userInfoVO.setCollege(user.getCollege());
        userInfoVO.setAvatar(user.getAvatar());
        userInfoVO.setCreditScore(user.getCreditScore());
        userInfoVO.setCreateTime(user.getCreateTime());

        return userInfoVO;
    }

    private void validatePassword(String password) {
        if (!StringUtils.hasText(password) || password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new BusinessException("密码长度必须在 " + MIN_PASSWORD_LENGTH + " 到 " + MAX_PASSWORD_LENGTH + " 位之间");
        }
    }
}
