package com.xiaou.hair.service;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.hair.dto.LoginDTO;
import com.xiaou.hair.dto.RegisterDTO;
import com.xiaou.hair.entity.User;
import com.xiaou.hair.mapper.UserMapper;
import com.xiaou.hair.util.JwtUtil;
import com.xiaou.hair.util.UserContext;
import com.xiaou.hair.vo.LoginVO;
import com.xiaou.hair.vo.UserInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 用户服务类
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户注册
     */
    @Transactional
    public void register(RegisterDTO dto) {
        // 检查手机号是否已注册
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, dto.getPhone());
        User existUser = userMapper.selectOne(wrapper);
        
        if (existUser != null) {
            throw new RuntimeException("该手机号已注册");
        }

        // 创建新用户
        User user = new User();
        user.setPhone(dto.getPhone());
        user.setPassword(BCrypt.hashpw(dto.getPassword()));  // BCrypt加密
        user.setNickname(dto.getNickname() != null ? dto.getNickname() : "用户" + dto.getPhone().substring(7));
        user.setAvatar("https://via.placeholder.com/150");
        user.setGender(0);
        user.setPoints(10);  // 注册奖励10积分
        user.setLevel("普通会员");
        user.setBalance(BigDecimal.ZERO);

        userMapper.insert(user);

        // 记录注册积分
        // TODO: 调用积分服务添加积分记录
    }

    /**
     * 用户登录
     */
    public LoginVO login(LoginDTO dto) {
        // 查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, dto.getPhone());
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证密码
        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 生成Token
        String token = jwtUtil.generateToken(user.getId(), user.getPhone());

        // 构建用户信息
        UserInfoVO userInfo = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfo);

        return new LoginVO(token, userInfo);
    }

    /**
     * 获取当前用户信息
     */
    public UserInfoVO getCurrentUserInfo() {
        Long userId = UserContext.getUserId();
        User user = userMapper.selectById(userId);
        
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        UserInfoVO vo = new UserInfoVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }

    /**
     * 更新用户信息
     */
    @Transactional
    public void updateUserInfo(UserInfoVO vo) {
        Long userId = UserContext.getUserId();
        User user = userMapper.selectById(userId);
        
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 只允许更新部分字段
        user.setNickname(vo.getNickname());
        user.setAvatar(vo.getAvatar());
        user.setGender(vo.getGender());

        userMapper.updateById(user);
    }

    /**
     * 添加积分
     */
    @Transactional
    public void addPoints(Long userId, Integer points, String reason) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        user.setPoints(user.getPoints() + points);
        
        // 自动升级会员等级
        updateUserLevel(user);
        
        userMapper.updateById(user);

        // TODO: 添加积分记录
    }

    /**
     * 扣减积分
     */
    @Transactional
    public void deductPoints(Long userId, Integer points, String reason) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (user.getPoints() < points) {
            throw new RuntimeException("积分不足");
        }

        user.setPoints(user.getPoints() - points);
        updateUserLevel(user);
        userMapper.updateById(user);

        // TODO: 添加积分记录
    }

    /**
     * 更新会员等级
     */
    private void updateUserLevel(User user) {
        Integer points = user.getPoints();
        String level;

        if (points >= 2000) {
            level = "钻石会员";
        } else if (points >= 500) {
            level = "金卡会员";
        } else if (points >= 100) {
            level = "银卡会员";
        } else {
            level = "普通会员";
        }

        user.setLevel(level);
    }

    /**
     * 获取会员折扣
     */
    public BigDecimal getMemberDiscount(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return BigDecimal.ONE;
        }

        return switch (user.getLevel()) {
            case "钻石会员" -> new BigDecimal("0.85");
            case "金卡会员" -> new BigDecimal("0.90");
            case "银卡会员" -> new BigDecimal("0.95");
            default -> BigDecimal.ONE;
        };
    }

    /**
     * 充值余额
     */
    @Transactional
    public void rechargeBalance(BigDecimal amount) {
        Long userId = UserContext.getUserId();
        User user = userMapper.selectById(userId);
        
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        user.setBalance(user.getBalance().add(amount));
        userMapper.updateById(user);

        // TODO: 添加余额记录
    }

    /**
     * 扣减余额
     */
    @Transactional
    public void deductBalance(Long userId, BigDecimal amount, String reason) {
        User user = userMapper.selectById(userId);
        
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (user.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("余额不足");
        }

        user.setBalance(user.getBalance().subtract(amount));
        userMapper.updateById(user);

        // TODO: 添加余额记录
    }
}
