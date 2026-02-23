package com.property.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.property.common.BusinessException;
import com.property.common.PageResult;
import com.property.entity.User;
import com.property.mapper.UserMapper;
import com.property.utils.JwtUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Map<String, Object> login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new BusinessException("用户名或密码不能为空");
        }
        username = username.trim();
        password = password.trim();
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!password.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        if (user.getStatus() == null || user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        userMapper.updateLastLoginTime(user.getId());
        String token = JwtUtils.generateToken(String.valueOf(user.getId()), user.getRole());
        redisTemplate.opsForValue().set("property:token:" + user.getId(), token, 24, TimeUnit.HOURS);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", safeUser(user));
        return map;
    }

    public User register(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty() || user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new BusinessException("用户名和密码不能为空");
        }
        user.setUsername(user.getUsername().trim());
        user.setPassword(user.getPassword().trim());
        if (userMapper.selectByUsername(user.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setNickname(user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname().trim());
        user.setRole("OWNER");
        user.setStatus(1);
        userMapper.insert(user);
        return safeUser(user);
    }

    public User getById(Long id) {
        return safeUser(userMapper.selectById(id));
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        if (oldPassword == null || oldPassword.trim().isEmpty() || newPassword == null || newPassword.trim().isEmpty()) {
            throw new BusinessException("密码不能为空");
        }
        oldPassword = oldPassword.trim();
        newPassword = newPassword.trim();
        if (oldPassword.equals(newPassword)) {
            throw new BusinessException("新旧密码不能一致");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!oldPassword.equals(user.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        userMapper.updatePassword(userId, newPassword);
        redisTemplate.delete("property:token:" + userId);
    }

    public void updateProfile(Long userId, User profile) {
        if (profile == null || profile.getNickname() == null || profile.getNickname().trim().isEmpty()) {
            throw new BusinessException("昵称不能为空");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setNickname(profile.getNickname().trim());
        user.setPhone(profile.getPhone() == null ? null : profile.getPhone().trim());
        user.setEmail(profile.getEmail() == null ? null : profile.getEmail().trim());
        userMapper.updateProfile(user);
    }

    public void logout(Long userId) {
        redisTemplate.delete("property:token:" + userId);
    }

    public PageResult<User> page(Integer pageNum, Integer pageSize, String username, String role, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectPageList(username, role, status);
        list.forEach(item -> item.setPassword(null));
        PageInfo<User> pageInfo = new PageInfo<>(list);
        PageResult<User> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(pageInfo.getList());
        return result;
    }

    public List<User> listByRole(String role) {
        List<User> list = userMapper.selectPageList(null, role, 1);
        list.forEach(item -> item.setPassword(null));
        return list;
    }

    public void save(User user) {
        if (user == null) {
            throw new BusinessException("用户参数不能为空");
        }
        if (user.getId() == null) {
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                throw new BusinessException("用户名不能为空");
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                throw new BusinessException("密码不能为空");
            }
            user.setUsername(user.getUsername().trim());
            user.setPassword(user.getPassword().trim());
            if (userMapper.selectByUsername(user.getUsername()) != null) {
                throw new BusinessException("用户名已存在");
            }
            user.setNickname(user.getNickname() == null || user.getNickname().trim().isEmpty() ? user.getUsername() : user.getNickname().trim());
            user.setRole(normalizeRole(user.getRole(), "OWNER"));
            user.setStatus(user.getStatus() == null ? 1 : user.getStatus());
            userMapper.insert(user);
        } else {
            User db = userMapper.selectById(user.getId());
            if (db == null) {
                throw new BusinessException("用户不存在");
            }
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                throw new BusinessException("用户名不能为空");
            }
            user.setUsername(user.getUsername().trim());
            User exist = userMapper.selectByUsername(user.getUsername());
            if (exist != null && !exist.getId().equals(user.getId())) {
                throw new BusinessException("用户名已存在");
            }
            if (user.getNickname() == null || user.getNickname().trim().isEmpty()) {
                throw new BusinessException("昵称不能为空");
            }
            user.setNickname(user.getNickname().trim());
            String newRole = normalizeRole(user.getRole(), db.getRole());
            if (db.getId() == 1L && !"ADMIN".equals(newRole)) {
                throw new BusinessException("默认管理员角色不可修改");
            }
            user.setRole(newRole);
            user.setStatus(user.getStatus() == null ? db.getStatus() : user.getStatus());
            if (db.getId() == 1L && user.getStatus() != null && user.getStatus() == 0) {
                throw new BusinessException("默认管理员不可禁用");
            }
            boolean invalidate = false;
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                user.setPassword(db.getPassword());
            } else {
                user.setPassword(user.getPassword().trim());
                invalidate = true;
            }
            if (!db.getRole().equals(newRole)) {
                invalidate = true;
            }
            if (!Objects.equals(db.getStatus(), user.getStatus()) && user.getStatus() != null && user.getStatus() == 0) {
                invalidate = true;
            }
            userMapper.update(user);
            if (invalidate) {
                redisTemplate.delete("property:token:" + user.getId());
            }
        }
    }

    public void updateStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("状态不合法");
        }
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (id == 1L && status == 0) {
            throw new BusinessException("默认管理员不可禁用");
        }
        userMapper.updateStatus(id, status);
        if (status == 0) {
            redisTemplate.delete("property:token:" + id);
        }
    }

    public void deleteById(Long id) {
        if (id == 1L) {
            throw new BusinessException("默认管理员不可删除");
        }
        userMapper.deleteById(id);
        redisTemplate.delete("property:token:" + id);
    }

    public Long countAll() {
        Long count = userMapper.countAll();
        return count == null ? 0L : count;
    }

    private String normalizeRole(String role, String fallback) {
        String r = role == null || role.trim().isEmpty() ? fallback : role.trim();
        if (!"ADMIN".equals(r) && !"STAFF".equals(r) && !"OWNER".equals(r)) {
            throw new BusinessException("角色不合法");
        }
        return r;
    }

    private User safeUser(User user) {
        if (user == null) {
            return null;
        }
        user.setPassword(null);
        return user;
    }
}
