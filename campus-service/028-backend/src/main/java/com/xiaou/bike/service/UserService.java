package com.xiaou.bike.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.bike.common.BusinessException;
import com.xiaou.bike.dto.LoginDTO;
import com.xiaou.bike.dto.RegisterDTO;
import com.xiaou.bike.entity.User;
import com.xiaou.bike.entity.UserWallet;
import com.xiaou.bike.mapper.UserMapper;
import com.xiaou.bike.mapper.UserWalletMapper;
import com.xiaou.bike.util.JwtUtil;
import com.xiaou.bike.util.UserContext;
import com.xiaou.bike.vo.LoginVO;
import com.xiaou.bike.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 用户服务
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserWalletMapper userWalletMapper;
    private final JwtUtil jwtUtil;

    @Value("${credit.initial:100}")
    private Integer initialCredit;

    /**
     * 用户注册
     */
    @Transactional
    public void register(RegisterDTO dto) {
        // 检查用户名是否存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        if (userMapper.selectCount(wrapper) > 0) {
            throw BusinessException.of("用户名已存在");
        }

        // 创建用户
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(BCrypt.hashpw(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setRealName(dto.getRealName());
        user.setStudentId(dto.getStudentId());
        user.setCreditScore(initialCredit);
        user.setStatus(1);
        user.setAuthStatus(0);
        userMapper.insert(user);

        // 创建用户钱包
        UserWallet wallet = new UserWallet();
        wallet.setUserId(user.getId());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setDeposit(BigDecimal.ZERO);
        wallet.setDepositStatus(0);
        userWalletMapper.insert(wallet);
    }

    /**
     * 用户登录
     */
    public LoginVO login(LoginDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            throw BusinessException.of("用户名或密码错误");
        }

        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            throw BusinessException.of("用户名或密码错误");
        }

        if (user.getStatus() == 0) {
            throw BusinessException.of("账号已被禁用");
        }

        // 生成Token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), "user");

        LoginVO vo = new LoginVO();
        vo.setToken(token);
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setRealName(user.getRealName());
        vo.setAvatar(user.getAvatar());
        vo.setUserType("user");
        return vo;
    }

    /**
     * 获取当前用户信息
     */
    public UserInfoVO getUserInfo() {
        Long userId = UserContext.getUserId();
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw BusinessException.of("用户不存在");
        }

        UserInfoVO vo = new UserInfoVO();
        BeanUtil.copyProperties(user, vo);

        // 获取钱包信息
        LambdaQueryWrapper<UserWallet> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserWallet::getUserId, userId);
        UserWallet wallet = userWalletMapper.selectOne(wrapper);
        if (wallet != null) {
            vo.setBalance(wallet.getBalance());
            vo.setDeposit(wallet.getDeposit());
            vo.setDepositStatus(wallet.getDepositStatus());
        }

        return vo;
    }

    /**
     * 更新用户信息
     */
    public void updateUserInfo(User user) {
        Long userId = UserContext.getUserId();
        user.setId(userId);
        user.setPassword(null); // 不更新密码
        user.setUsername(null); // 不更新用户名
        user.setCreditScore(null); // 不更新信用分
        user.setStatus(null); // 不更新状态
        userMapper.updateById(user);
    }

    /**
     * 修改密码
     */
    public void changePassword(String oldPassword, String newPassword) {
        Long userId = UserContext.getUserId();
        User user = userMapper.selectById(userId);
        
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw BusinessException.of("原密码错误");
        }

        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setPassword(BCrypt.hashpw(newPassword));
        userMapper.updateById(updateUser);
    }

    /**
     * 实名认证申请
     */
    public void submitAuth(String realName, String idCard, String authImage) {
        Long userId = UserContext.getUserId();
        User user = new User();
        user.setId(userId);
        user.setRealName(realName);
        user.setIdCard(idCard);
        user.setAuthImage(authImage);
        user.setAuthStatus(1); // 待审核
        userMapper.updateById(user);
    }

    /**
     * 分页查询用户列表（管理端）
     */
    public Page<User> listUsers(Integer page, Integer size, String keyword, Integer status) {
        Page<User> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(w -> w.like(User::getUsername, keyword)
                    .or().like(User::getRealName, keyword)
                    .or().like(User::getPhone, keyword)
                    .or().like(User::getStudentId, keyword));
        }
        
        if (status != null) {
            wrapper.eq(User::getStatus, status);
        }
        
        wrapper.orderByDesc(User::getCreateTime);
        return userMapper.selectPage(pageParam, wrapper);
    }

    /**
     * 更新用户状态（管理端）
     */
    public void updateStatus(Long userId, Integer status) {
        User user = new User();
        user.setId(userId);
        user.setStatus(status);
        userMapper.updateById(user);
    }

    /**
     * 审核实名认证（管理端）
     */
    public void auditAuth(Long userId, Integer authStatus) {
        User user = new User();
        user.setId(userId);
        user.setAuthStatus(authStatus);
        userMapper.updateById(user);
    }

    /**
     * 调整信用分（管理端）
     */
    public void adjustCredit(Long userId, Integer creditScore) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw BusinessException.of("用户不存在");
        }
        
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setCreditScore(creditScore);
        userMapper.updateById(updateUser);
    }
}
