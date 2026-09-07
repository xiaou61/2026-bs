package com.xiaou.express.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.express.common.BusinessException;
import com.xiaou.express.dto.LoginRequest;
import com.xiaou.express.dto.RegisterRequest;
import com.xiaou.express.entity.Admin;
import com.xiaou.express.entity.User;
import com.xiaou.express.entity.Wallet;
import com.xiaou.express.mapper.AdminMapper;
import com.xiaou.express.mapper.UserMapper;
import com.xiaou.express.mapper.WalletMapper;
import com.xiaou.express.util.JwtUtil;
import com.xiaou.express.vo.LoginVO;
import cn.hutool.crypto.digest.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public void register(RegisterRequest request) {
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
        user.setDormitoryBuilding(request.getDormitoryBuilding());
        user.setDormitoryRoom(request.getDormitoryRoom());
        user.setCreditScore(100);
        user.setTotalOrders(0);
        user.setTotalTakes(0);
        user.setStatus(0);
        userMapper.insert(user);

        Wallet wallet = new Wallet();
        wallet.setUserId(user.getId());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setFrozenAmount(BigDecimal.ZERO);
        wallet.setTotalIncome(BigDecimal.ZERO);
        wallet.setTotalExpense(BigDecimal.ZERO);
        walletMapper.insert(wallet);
    }

    public LoginVO login(LoginRequest request) {
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

        String token = jwtUtil.generateToken(user.getId(), "user");
        return new LoginVO(token, user.getId(), user.getUsername(), user.getRealName());
    }

    public LoginVO adminLogin(LoginRequest request) {
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

        String token = jwtUtil.generateToken(admin.getId(), "admin");
        return new LoginVO(token, admin.getId(), admin.getUsername(), admin.getRealName());
    }
}

