package com.teacher.new.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teacher.new.common.BusinessException;
import com.teacher.new.common.PageResult;
import com.teacher.new.entity.User;
import com.teacher.new.mapper.UserMapper;
import com.teacher.new.utils.JwtUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
            throw new BusinessException("账号已被禁用");
        }
        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateById(user);
        String token = JwtUtils.generateToken(String.valueOf(user.getId()), user.getRole());
        redisTemplate.opsForValue().set("teacher:token:" + user.getId(), token, 24, TimeUnit.HOURS);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", safeUser(user));
        return map;
    }

    public User register(User user) {
        if (user == null || !StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword()) || !StringUtils.hasText(user.getNickname())) {
            throw new BusinessException("注册参数不完整");
        }
        String username = user.getUsername().trim();
        if (userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username).last("limit 1")) != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setUsername(username);
        user.setPassword(user.getPassword().trim());
        user.setNickname(user.getNickname().trim());
        user.setPhone(StringUtils.hasText(user.getPhone()) ? user.getPhone().trim() : "");
        user.setEmail(StringUtils.hasText(user.getEmail()) ? user.getEmail().trim() : "");
        user.setRole("STUDENT");
        user.setStatus(1);
        userMapper.insert(user);
        return safeUser(user);
    }

    public User getById(Long id) {
        return safeUser(userMapper.selectById(id));
    }

    public User getRawById(Long id) {
        return userMapper.selectById(id);
    }

    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        if (!StringUtils.hasText(oldPassword) || !StringUtils.hasText(newPassword)) {
            throw new BusinessException("密码不能为空");
        }
        User db = userMapper.selectById(userId);
        if (db == null) {
            throw new BusinessException("用户不存在");
        }
        if (!oldPassword.trim().equals(db.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        if (oldPassword.trim().equals(newPassword.trim())) {
            throw new BusinessException("新旧密码不能一致");
        }
        db.setPassword(newPassword.trim());
        userMapper.updateById(db);
        redisTemplate.delete("teacher:token:" + userId);
    }

    public void updateProfile(Long userId, User profile) {
        if (profile == null || !StringUtils.hasText(profile.getNickname())) {
            throw new BusinessException("昵称不能为空");
        }
        User db = userMapper.selectById(userId);
        if (db == null) {
            throw new BusinessException("用户不存在");
        }
        db.setNickname(profile.getNickname().trim());
        db.setPhone(StringUtils.hasText(profile.getPhone()) ? profile.getPhone().trim() : "");
        db.setEmail(StringUtils.hasText(profile.getEmail()) ? profile.getEmail().trim() : "");
        userMapper.updateById(db);
    }

    public void logout(Long userId) {
        redisTemplate.delete("teacher:token:" + userId);
    }

    public PageResult<User> page(Integer pageNum, Integer pageSize, String username, String role, Integer status, Long classId) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .like(StringUtils.hasText(username), User::getUsername, username == null ? null : username.trim())
                .eq(StringUtils.hasText(role), User::getRole, role == null ? null : role.trim())
                .eq(status != null, User::getStatus, status)
                .eq(classId != null, User::getClassId, classId)
                .orderByDesc(User::getId);
        Page<User> resultPage = userMapper.selectPage(page, wrapper);
        List<User> list = resultPage.getRecords();
        list.forEach(item -> item.setPassword(null));
        PageResult<User> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(list);
        return result;
    }

    public void save(User user) {
        if (user == null) {
            throw new BusinessException("用户参数不能为空");
        }
        if (user.getId() == null) {
            add(user);
        } else {
            update(user);
        }
    }

    private void add(User user) {
        if (!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword()) || !StringUtils.hasText(user.getRole())) {
            throw new BusinessException("参数不完整");
        }
        String username = user.getUsername().trim();
        if (userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username).last("limit 1")) != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setUsername(username);
        user.setPassword(user.getPassword().trim());
        user.setNickname(StringUtils.hasText(user.getNickname()) ? user.getNickname().trim() : username);
        user.setPhone(StringUtils.hasText(user.getPhone()) ? user.getPhone().trim() : "");
        user.setEmail(StringUtils.hasText(user.getEmail()) ? user.getEmail().trim() : "");
        user.setRole(normalizeRole(user.getRole(), "STUDENT"));
        user.setStatus(user.getStatus() == null ? 1 : user.getStatus());
        if (!"STUDENT".equals(user.getRole())) {
            user.setClassId(null);
        }
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
        User exist = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername().trim())
                .last("limit 1"));
        if (exist != null && !exist.getId().equals(user.getId())) {
            throw new BusinessException("用户名已存在");
        }
        String role = normalizeRole(user.getRole(), db.getRole());
        Integer status = user.getStatus() == null ? db.getStatus() : user.getStatus();
        if (db.getId() == 1L && !"ADMIN".equals(role)) {
            throw new BusinessException("默认管理员角色不可修改");
        }
        if (db.getId() == 1L && status == 0) {
            throw new BusinessException("默认管理员不可禁用");
        }
        String oldRole = db.getRole();
        Integer oldStatus = db.getStatus();
        boolean invalidate = false;
        db.setUsername(user.getUsername().trim());
        db.setNickname(StringUtils.hasText(user.getNickname()) ? user.getNickname().trim() : db.getUsername());
        db.setPhone(StringUtils.hasText(user.getPhone()) ? user.getPhone().trim() : "");
        db.setEmail(StringUtils.hasText(user.getEmail()) ? user.getEmail().trim() : "");
        db.setRole(role);
        db.setStatus(status);
        db.setClassId("STUDENT".equals(role) ? user.getClassId() : null);
        if (StringUtils.hasText(user.getPassword())) {
            db.setPassword(user.getPassword().trim());
            invalidate = true;
        }
        if (!Objects.equals(oldRole, role)) {
            invalidate = true;
        }
        if (!Objects.equals(oldStatus, status) && status == 0) {
            invalidate = true;
        }
        userMapper.updateById(db);
        if (invalidate) {
            redisTemplate.delete("teacher:token:" + db.getId());
        }
    }

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
            redisTemplate.delete("teacher:token:" + id);
        }
    }

    public void deleteById(Long id) {
        if (id == 1L) {
            throw new BusinessException("默认管理员不可删除");
        }
        userMapper.deleteById(id);
        redisTemplate.delete("teacher:token:" + id);
    }

    public Long countAll() {
        return userMapper.selectCount(null);
    }

    public Long countByRole(String role) {
        return userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getRole, role));
    }

    private String normalizeRole(String role, String fallback) {
        String r = StringUtils.hasText(role) ? role.trim() : fallback;
        if (!"ADMIN".equals(r) && !"TEACHER".equals(r) && !"STUDENT".equals(r)) {
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
