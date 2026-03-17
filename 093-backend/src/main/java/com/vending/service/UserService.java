package com.vending.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vending.common.BusinessException;
import com.vending.common.PageResult;
import com.vending.entity.User;
import com.vending.mapper.UserMapper;
import com.vending.utils.JwtUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Map<String, Object> login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new BusinessException("用户名或密码不能为空");
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username.trim())
                .last("limit 1"));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!password.trim().equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        if (user.getStatus() == null || user.getStatus() == 0) {
            throw new BusinessException("账号已禁用");
        }
        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateById(user);
        String token = JwtUtils.generateToken(String.valueOf(user.getId()), user.getRole());
        redisTemplate.opsForValue().set("vending:token:" + user.getId(), token, 24, TimeUnit.HOURS);
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", safeUser(user));
        return data;
    }

    public User register(User user) {
        if (user == null || !StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            throw new BusinessException("用户名和密码不能为空");
        }
        user.setRole("CUSTOMER");
        user.setStatus(1);
        prepareUserData(user, true);
        validateUnique(user);
        userMapper.insert(user);
        return safeUser(user);
    }

    public User getById(Long id) {
        return safeUser(requireById(id));
    }

    public User requireById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }

    public void logout(Long userId) {
        redisTemplate.delete("vending:token:" + userId);
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        if (!StringUtils.hasText(oldPassword) || !StringUtils.hasText(newPassword)) {
            throw new BusinessException("密码不能为空");
        }
        User db = requireById(userId);
        if (!oldPassword.trim().equals(db.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        if (oldPassword.trim().equals(newPassword.trim())) {
            throw new BusinessException("新旧密码不能一致");
        }
        db.setPassword(newPassword.trim());
        userMapper.updateById(db);
        redisTemplate.delete("vending:token:" + userId);
    }

    public void updateProfile(Long userId, User profile) {
        User db = requireById(userId);
        if (!StringUtils.hasText(profile.getNickname())) {
            throw new BusinessException("昵称不能为空");
        }
        if (StringUtils.hasText(profile.getPhone())) {
            User phoneUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                    .eq(User::getPhone, profile.getPhone().trim())
                    .last("limit 1"));
            if (phoneUser != null && !phoneUser.getId().equals(userId)) {
                throw new BusinessException("手机号已存在");
            }
        }
        db.setNickname(profile.getNickname().trim());
        db.setPhone(StringUtils.hasText(profile.getPhone()) ? profile.getPhone().trim() : null);
        db.setEmail(StringUtils.hasText(profile.getEmail()) ? profile.getEmail().trim() : null);
        db.setAvatar(StringUtils.hasText(profile.getAvatar()) ? profile.getAvatar().trim() : null);
        userMapper.updateById(db);
    }

    public PageResult<User> page(Integer pageNum, Integer pageSize, String username, String phone, String role, Integer status) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .like(StringUtils.hasText(username), User::getUsername, username == null ? null : username.trim())
                .like(StringUtils.hasText(phone), User::getPhone, phone == null ? null : phone.trim())
                .eq(StringUtils.hasText(role), User::getRole, role == null ? null : role.trim().toUpperCase())
                .eq(status != null, User::getStatus, status)
                .orderByDesc(User::getId);
        Page<User> resultPage = userMapper.selectPage(page, wrapper);
        resultPage.getRecords().forEach(item -> item.setPassword(null));
        PageResult<User> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public void save(User user) {
        if (user == null) {
            throw new BusinessException("参数不能为空");
        }
        if (user.getId() == null) {
            add(user);
        } else {
            update(user);
        }
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("状态不合法");
        }
        User db = requireById(id);
        if (id == 1L && status == 0) {
            throw new BusinessException("默认管理员不可禁用");
        }
        db.setStatus(status);
        userMapper.updateById(db);
        if (status == 0) {
            redisTemplate.delete("vending:token:" + id);
        }
    }

    public void deleteById(Long id) {
        if (id == 1L) {
            throw new BusinessException("默认管理员不可删除");
        }
        userMapper.deleteById(id);
        redisTemplate.delete("vending:token:" + id);
    }

    public void changeBalance(Long userId, BigDecimal delta) {
        if (delta == null || BigDecimal.ZERO.compareTo(delta) == 0) {
            throw new BusinessException("金额不能为0");
        }
        User user = requireById(userId);
        BigDecimal balance = user.getBalance() == null ? BigDecimal.ZERO : user.getBalance();
        BigDecimal newBalance = balance.add(delta);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("余额不足");
        }
        user.setBalance(newBalance);
        userMapper.updateById(user);
    }

    public void addConsumeAmount(Long userId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        User user = requireById(userId);
        BigDecimal totalConsume = user.getTotalConsume() == null ? BigDecimal.ZERO : user.getTotalConsume();
        user.setTotalConsume(totalConsume.add(amount));
        userMapper.updateById(user);
    }

    public Long countCustomers() {
        return userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getRole, "CUSTOMER"));
    }

    private void add(User user) {
        if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword())) {
            throw new BusinessException("用户名和密码不能为空");
        }
        prepareUserData(user, true);
        validateUnique(user);
        userMapper.insert(user);
    }

    private void update(User user) {
        User db = requireById(user.getId());
        if (!StringUtils.hasText(user.getUsername())) {
            throw new BusinessException("用户名不能为空");
        }
        validateUnique(user);
        if (db.getId() == 1L && user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException("默认管理员不可禁用");
        }
        db.setUsername(user.getUsername().trim());
        db.setNickname(StringUtils.hasText(user.getNickname()) ? user.getNickname().trim() : user.getUsername().trim());
        db.setPhone(StringUtils.hasText(user.getPhone()) ? user.getPhone().trim() : null);
        db.setEmail(StringUtils.hasText(user.getEmail()) ? user.getEmail().trim() : null);
        db.setAvatar(StringUtils.hasText(user.getAvatar()) ? user.getAvatar().trim() : null);
        if (StringUtils.hasText(user.getPassword())) {
            db.setPassword(user.getPassword().trim());
            redisTemplate.delete("vending:token:" + db.getId());
        }
        if (StringUtils.hasText(user.getRole())) {
            String role = user.getRole().trim().toUpperCase();
            if (!"ADMIN".equals(role) && !"STAFF".equals(role) && !"CUSTOMER".equals(role)) {
                throw new BusinessException("角色不合法");
            }
            if (db.getId() == 1L && !"ADMIN".equals(role)) {
                throw new BusinessException("默认管理员角色不可修改");
            }
            db.setRole(role);
        }
        if (user.getStatus() != null) {
            db.setStatus(user.getStatus());
            if (user.getStatus() == 0) {
                redisTemplate.delete("vending:token:" + db.getId());
            }
        }
        if (user.getBalance() != null) {
            db.setBalance(user.getBalance());
        }
        if (user.getTotalConsume() != null) {
            db.setTotalConsume(user.getTotalConsume());
        }
        userMapper.updateById(db);
    }

    private void prepareUserData(User user, boolean createUserNo) {
        String role = StringUtils.hasText(user.getRole()) ? user.getRole().trim().toUpperCase() : "CUSTOMER";
        if (!"ADMIN".equals(role) && !"STAFF".equals(role) && !"CUSTOMER".equals(role)) {
            throw new BusinessException("角色不合法");
        }
        user.setRole(role);
        user.setUsername(user.getUsername().trim());
        user.setPassword(user.getPassword().trim());
        user.setNickname(StringUtils.hasText(user.getNickname()) ? user.getNickname().trim() : user.getUsername());
        user.setPhone(StringUtils.hasText(user.getPhone()) ? user.getPhone().trim() : null);
        user.setEmail(StringUtils.hasText(user.getEmail()) ? user.getEmail().trim() : null);
        user.setAvatar(StringUtils.hasText(user.getAvatar()) ? user.getAvatar().trim() : null);
        user.setStatus(user.getStatus() == null ? 1 : user.getStatus());
        user.setBalance(user.getBalance() == null ? BigDecimal.ZERO : user.getBalance());
        user.setTotalConsume(user.getTotalConsume() == null ? BigDecimal.ZERO : user.getTotalConsume());
        if (createUserNo || !StringUtils.hasText(user.getUserNo())) {
            user.setUserNo("U" + System.currentTimeMillis() + RandomUtil.randomNumbers(4));
        }
    }

    private void validateUnique(User user) {
        User usernameUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername().trim())
                .last("limit 1"));
        if (usernameUser != null && (user.getId() == null || !usernameUser.getId().equals(user.getId()))) {
            throw new BusinessException("用户名已存在");
        }
        if (StringUtils.hasText(user.getPhone())) {
            User phoneUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                    .eq(User::getPhone, user.getPhone().trim())
                    .last("limit 1"));
            if (phoneUser != null && (user.getId() == null || !phoneUser.getId().equals(user.getId()))) {
                throw new BusinessException("手机号已存在");
            }
        }
    }

    private User safeUser(User user) {
        user.setPassword(null);
        return user;
    }
}
