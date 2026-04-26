package com.hospital.service;

import com.hospital.common.BusinessException;
import com.hospital.entity.MedicalCard;
import com.hospital.entity.PatientProfile;
import com.hospital.entity.SysUser;
import com.hospital.mapper.MedicalCardMapper;
import com.hospital.mapper.PatientProfileMapper;
import com.hospital.mapper.SysUserMapper;
import com.hospital.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PatientProfileMapper patientProfileMapper;

    @Autowired
    private MedicalCardMapper medicalCardMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private OperationLogService operationLogService;

    public Map<String, Object> login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new BusinessException("用户名和密码不能为空");
        }
        SysUser user = sysUserMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!password.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BusinessException("账号已禁用");
        }
        String token = jwtUtils.generateToken(user.getId(), user.getRole());
        stringRedisTemplate.opsForValue().set("TOKEN:" + user.getId(), token, 1, TimeUnit.DAYS);
        user.setPassword(null);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        operationLogService.record(user.getId(), user.getRole(), "认证", "登录", "用户登录系统");
        return result;
    }

    public void register(Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        String nickname = params.get("nickname");
        String phone = params.get("phone");
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password) || !StringUtils.hasText(nickname)) {
            throw new BusinessException("用户名、密码和昵称不能为空");
        }
        if (sysUserMapper.selectByUsername(username) != null) {
            throw new BusinessException("用户名已存在");
        }
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setRole("PATIENT");
        user.setStatus(1);
        user.setBalance(BigDecimal.ZERO);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        sysUserMapper.insert(user);

        PatientProfile profile = new PatientProfile();
        profile.setUserId(user.getId());
        profile.setRealName(nickname);
        profile.setCreateTime(LocalDateTime.now());
        profile.setUpdateTime(LocalDateTime.now());
        patientProfileMapper.insert(profile);

        MedicalCard card = new MedicalCard();
        card.setUserId(user.getId());
        card.setCardNo(generateCardNo());
        card.setPatientName(nickname);
        card.setPhone(phone);
        card.setIsDefault(1);
        card.setStatus(1);
        card.setCreateTime(LocalDateTime.now());
        medicalCardMapper.insert(card);
        operationLogService.record(user.getId(), user.getRole(), "认证", "注册", "患者完成注册");
    }

    public SysUser info(Long userId) {
        SysUser user = getById(userId);
        user.setPassword(null);
        return user;
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        SysUser user = getById(userId);
        if (!StringUtils.hasText(oldPassword) || !StringUtils.hasText(newPassword)) {
            throw new BusinessException("原密码和新密码不能为空");
        }
        if (!oldPassword.equals(user.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        user.setPassword(newPassword);
        user.setUpdateTime(LocalDateTime.now());
        sysUserMapper.update(user);
        operationLogService.record(userId, user.getRole(), "认证", "修改密码", "用户修改登录密码");
    }

    public SysUser getById(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }

    public void logout(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        stringRedisTemplate.delete("TOKEN:" + userId);
        if (user != null) {
            operationLogService.record(userId, user.getRole(), "认证", "退出", "用户退出系统");
        }
    }

    public void assertAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException(403, "无权限操作");
        }
    }

    public void assertDoctor(String role) {
        if (!"DOCTOR".equals(role) && !"ADMIN".equals(role)) {
            throw new BusinessException(403, "无权限操作");
        }
    }

    public void assertPatient(String role) {
        if (!"PATIENT".equals(role) && !"ADMIN".equals(role)) {
            throw new BusinessException(403, "无权限操作");
        }
    }

    private String generateCardNo() {
        return "MC" + System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(100, 999);
    }
}
