package com.xiaou.ordering.service;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.ordering.common.BusinessException;
import com.xiaou.ordering.dto.LoginRequest;
import com.xiaou.ordering.dto.RegisterRequest;
import com.xiaou.ordering.entity.Admin;
import com.xiaou.ordering.entity.Merchant;
import com.xiaou.ordering.entity.User;
import com.xiaou.ordering.mapper.AdminMapper;
import com.xiaou.ordering.mapper.MerchantMapper;
import com.xiaou.ordering.mapper.UserMapper;
import com.xiaou.ordering.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private JwtUtil jwtUtil;

    public void userRegister(RegisterRequest request) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getStudentId, request.getStudentId());
        if (userMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("学号已存在");
        }

        wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, request.getPhone());
        if (userMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("手机号已存在");
        }

        User user = new User();
        user.setStudentId(request.getStudentId());
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());
        user.setPassword(BCrypt.hashpw(request.getPassword()));
        user.setRealName(request.getRealName());
        user.setDepartment(request.getDepartment());
        user.setMajor(request.getMajor());
        user.setDormitory(request.getDormitory());
        user.setBalance(BigDecimal.ZERO);
        user.setStatus(0);
        userMapper.insert(user);
    }

    public String userLogin(LoginRequest request) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getStudentId, request.getUsername())
                .or()
                .eq(User::getPhone, request.getUsername());
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        if (user.getStatus() == 1) {
            throw new BusinessException("账号已被禁用");
        }

        return jwtUtil.generateToken(user.getId(), "user");
    }

    public String merchantLogin(LoginRequest request) {
        LambdaQueryWrapper<Merchant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Merchant::getContactPhone, request.getUsername());
        Merchant merchant = merchantMapper.selectOne(wrapper);

        if (merchant == null) {
            throw new BusinessException("商家不存在");
        }

        if (!BCrypt.checkpw(request.getPassword(), merchant.getPassword())) {
            throw new BusinessException("密码错误");
        }

        if (merchant.getStatus() == 3) {
            throw new BusinessException("商家已被禁用");
        }

        if (merchant.getAuditStatus() != 1) {
            throw new BusinessException("商家未通过审核");
        }

        return jwtUtil.generateToken(merchant.getId(), "merchant");
    }

    public String adminLogin(LoginRequest request) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUsername, request.getUsername());
        Admin admin = adminMapper.selectOne(wrapper);

        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }

        if (!BCrypt.checkpw(request.getPassword(), admin.getPassword())) {
            throw new BusinessException("密码错误");
        }

        if (admin.getStatus() == 1) {
            throw new BusinessException("账号已被禁用");
        }

        return jwtUtil.generateToken(admin.getId(), "admin");
    }
}

