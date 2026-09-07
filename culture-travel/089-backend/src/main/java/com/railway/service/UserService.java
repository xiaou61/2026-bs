package com.railway.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.railway.common.BusinessException;
import com.railway.common.PageResult;
import com.railway.entity.User;
import com.railway.mapper.UserMapper;
import com.railway.utils.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 64;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RuntimeStoreService runtimeStoreService;

    @Resource
    private JwtUtils jwtUtils;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Map<String, Object> login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new BusinessException("用户名或密码不能为空");
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username.trim()));
        if (user == null || !passwordEncoder.matches(password.trim(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() == null || user.getStatus() == 0) {
            throw new BusinessException("账号已禁用");
        }
        String token = jwtUtils.generateToken(String.valueOf(user.getId()), user.getRole());
        runtimeStoreService.storeToken(user.getId(), token);
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", safeUser(user));
        return data;
    }

    @Transactional
    public User register(User user) {
        if (user == null || !StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            throw new BusinessException("用户名和密码不能为空");
        }
        validatePassword(user.getPassword().trim());
        assertUsernameUnique(user.getUsername().trim(), null);
        assertPhoneUnique(user.getPhone(), null);
        user.setUsername(user.getUsername().trim());
        user.setPassword(passwordEncoder.encode(user.getPassword().trim()));
        user.setNickname(StringUtils.hasText(user.getNickname()) ? user.getNickname().trim() : user.getUsername());
        user.setPhone(StringUtils.hasText(user.getPhone()) ? user.getPhone().trim() : null);
        user.setEmail(StringUtils.hasText(user.getEmail()) ? user.getEmail().trim() : null);
        user.setAvatar(StringUtils.hasText(user.getAvatar()) ? user.getAvatar().trim() : null);
        user.setRole("USER");
        user.setStatus(1);
        user.setBalance(BigDecimal.ZERO);
        userMapper.insert(user);
        return safeUser(user);
    }

    public User getById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return safeUser(user);
    }

    public void logout(Long userId) {
        runtimeStoreService.removeToken(userId);
    }

    @Transactional
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        if (!StringUtils.hasText(oldPassword) || !StringUtils.hasText(newPassword)) {
            throw new BusinessException("密码不能为空");
        }
        validatePassword(newPassword.trim());
        User db = userMapper.selectById(userId);
        if (db == null) {
            throw new BusinessException("用户不存在");
        }
        if (!passwordEncoder.matches(oldPassword.trim(), db.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        if (oldPassword.trim().equals(newPassword.trim())) {
            throw new BusinessException("新旧密码不能一致");
        }
        db.setPassword(passwordEncoder.encode(newPassword.trim()));
        userMapper.updateById(db);
        runtimeStoreService.removeToken(userId);
    }

    @Transactional
    public void updateProfile(Long userId, User profile) {
        User db = userMapper.selectById(userId);
        if (db == null) {
            throw new BusinessException("用户不存在");
        }
        if (!StringUtils.hasText(profile.getNickname())) {
            throw new BusinessException("昵称不能为空");
        }
        assertPhoneUnique(profile.getPhone(), userId);
        db.setNickname(profile.getNickname().trim());
        db.setPhone(StringUtils.hasText(profile.getPhone()) ? profile.getPhone().trim() : null);
        db.setEmail(StringUtils.hasText(profile.getEmail()) ? profile.getEmail().trim() : null);
        db.setAvatar(StringUtils.hasText(profile.getAvatar()) ? profile.getAvatar().trim() : null);
        userMapper.updateById(db);
    }

    public PageResult<User> page(Integer pageNum, Integer pageSize, String username, String phone, String role, Integer status) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .like(StringUtils.hasText(username), User::getUsername, trim(username))
                .like(StringUtils.hasText(phone), User::getPhone, trim(phone))
                .eq(StringUtils.hasText(role), User::getRole, trimUpper(role))
                .eq(status != null, User::getStatus, status)
                .orderByDesc(User::getId);
        Page<User> resultPage = userMapper.selectPage(page, wrapper);
        resultPage.getRecords().forEach(this::safeUser);
        PageResult<User> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    @Transactional
    public void save(User user) {
        if (user == null) {
            throw new BusinessException("参数不能为空");
        }
        if (user.getId() == null) {
            add(user);
            return;
        }
        update(user);
    }

    @Transactional
    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("状态不合法");
        }
        User db = userMapper.selectById(id);
        if (db == null) {
            throw new BusinessException("用户不存在");
        }
        if (id == 1L && status == 0) {
            throw new BusinessException("默认管理员不可禁用");
        }
        db.setStatus(status);
        userMapper.updateById(db);
        if (status == 0) {
            runtimeStoreService.removeToken(id);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (id == 1L) {
            throw new BusinessException("默认管理员不可删除");
        }
        userMapper.deleteById(id);
        runtimeStoreService.removeToken(id);
    }

    @Transactional
    public void changeBalance(Long userId, BigDecimal delta) {
        if (delta == null || BigDecimal.ZERO.compareTo(delta) == 0) {
            throw new BusinessException("金额不能为0");
        }
        int affected = userMapper.changeBalance(userId, delta);
        if (affected == 0) {
            throw new BusinessException("余额不足或用户不存在");
        }
    }

    public Long countAll() {
        return userMapper.selectCount(null);
    }

    private void add(User user) {
        if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            throw new BusinessException("用户名和密码不能为空");
        }
        validatePassword(user.getPassword().trim());
        String username = user.getUsername().trim();
        assertUsernameUnique(username, null);
        assertPhoneUnique(user.getPhone(), null);
        String role = trimUpper(user.getRole());
        if (!"ADMIN".equals(role) && !"DISPATCHER".equals(role) && !"USER".equals(role)) {
            throw new BusinessException("角色不合法");
        }
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(user.getPassword().trim()));
        user.setNickname(StringUtils.hasText(user.getNickname()) ? user.getNickname().trim() : username);
        user.setPhone(StringUtils.hasText(user.getPhone()) ? user.getPhone().trim() : null);
        user.setEmail(StringUtils.hasText(user.getEmail()) ? user.getEmail().trim() : null);
        user.setAvatar(StringUtils.hasText(user.getAvatar()) ? user.getAvatar().trim() : null);
        user.setRole(role);
        user.setStatus(user.getStatus() == null ? 1 : user.getStatus());
        user.setBalance(user.getBalance() == null ? BigDecimal.ZERO : user.getBalance());
        userMapper.insert(user);
    }

    private void update(User user) {
        User db = userMapper.selectById(user.getId());
        if (db == null) {
            throw new BusinessException("用户不存在");
        }
        if (!StringUtils.hasText(user.getUsername())) {
            throw new BusinessException("用户名不能为空");
        }
        String username = user.getUsername().trim();
        assertUsernameUnique(username, user.getId());
        assertPhoneUnique(user.getPhone(), user.getId());
        db.setUsername(username);
        db.setNickname(StringUtils.hasText(user.getNickname()) ? user.getNickname().trim() : username);
        db.setPhone(StringUtils.hasText(user.getPhone()) ? user.getPhone().trim() : null);
        db.setEmail(StringUtils.hasText(user.getEmail()) ? user.getEmail().trim() : null);
        db.setAvatar(StringUtils.hasText(user.getAvatar()) ? user.getAvatar().trim() : null);
        if (StringUtils.hasText(user.getPassword())) {
            validatePassword(user.getPassword().trim());
            db.setPassword(passwordEncoder.encode(user.getPassword().trim()));
            runtimeStoreService.removeToken(db.getId());
        }
        if (StringUtils.hasText(user.getRole())) {
            String role = trimUpper(user.getRole());
            if (!"ADMIN".equals(role) && !"DISPATCHER".equals(role) && !"USER".equals(role)) {
                throw new BusinessException("角色不合法");
            }
            if (db.getId() == 1L && !"ADMIN".equals(role)) {
                throw new BusinessException("默认管理员角色不可修改");
            }
            if (!role.equals(db.getRole())) {
                db.setRole(role);
                runtimeStoreService.removeToken(db.getId());
            }
        }
        if (user.getStatus() != null) {
            db.setStatus(user.getStatus());
            if (user.getStatus() == 0) {
                runtimeStoreService.removeToken(db.getId());
            }
        }
        if (user.getBalance() != null) {
            db.setBalance(user.getBalance());
        }
        userMapper.updateById(db);
    }

    private void assertUsernameUnique(String username, Long excludeId) {
        if (!StringUtils.hasText(username)) {
            return;
        }
        User exist = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username.trim()));
        if (exist != null && (excludeId == null || !exist.getId().equals(excludeId))) {
            throw new BusinessException("用户名已存在");
        }
    }

    private void assertPhoneUnique(String phone, Long excludeId) {
        if (!StringUtils.hasText(phone)) {
            return;
        }
        User exist = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, phone.trim()));
        if (exist != null && (excludeId == null || !exist.getId().equals(excludeId))) {
            throw new BusinessException("手机号已存在");
        }
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }

    private String trimUpper(String value) {
        return value == null ? null : value.trim().toUpperCase();
    }

    private User safeUser(User user) {
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    private void validatePassword(String password) {
        if (!StringUtils.hasText(password) || password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new BusinessException("密码长度必须在 " + MIN_PASSWORD_LENGTH + " 到 " + MAX_PASSWORD_LENGTH + " 位之间");
        }
    }
}
