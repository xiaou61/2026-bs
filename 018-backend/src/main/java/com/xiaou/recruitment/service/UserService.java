package com.xiaou.recruitment.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.recruitment.entity.Company;
import com.xiaou.recruitment.entity.User;
import com.xiaou.recruitment.mapper.UserMapper;
import com.xiaou.recruitment.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private CompanyService companyService;

    public User register(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        if (getOne(wrapper) != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        user.setPassword(PasswordUtil.encrypt(user.getPassword()));
        save(user);
        if ("company".equals(user.getRole())) {
            Company company = new Company();
            company.setName(StringUtils.hasText(user.getCompanyName()) ? user.getCompanyName() : user.getRealName() + "招聘团队");
            company.setStatus(0);
            companyService.save(company);
            user.setCompanyId(company.getId());
            updateById(user);
        }
        return getById(user.getId());
    }

    public User login(String username, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = getOne(wrapper);
        if (user != null && PasswordUtil.verify(password, user.getPassword()) && user.getStatus() != null
                && user.getStatus() == 1) {
            return user;
        }
        return null;
    }

    public User getUserById(Long id) {
        return getById(id);
    }

    public boolean updateUser(Long userId, User payload) {
        User user = getById(userId);
        if (user == null) {
            return false;
        }
        user.setRealName(payload.getRealName());
        user.setEmail(payload.getEmail());
        user.setPhone(payload.getPhone());
        user.setAvatar(payload.getAvatar());
        if (StringUtils.hasText(payload.getPassword())) {
            user.setPassword(PasswordUtil.encrypt(payload.getPassword()));
        }
        return updateById(user);
    }
}
