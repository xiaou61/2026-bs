package com.xiaou.rice.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.rice.modules.user.entity.User;
import com.xiaou.rice.modules.user.mapper.UserMapper;
import com.xiaou.rice.modules.user.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserDetailsServiceImpl(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.getStatus());
    }

    public UserPrincipal loadUserByUsernameAndId(String username, Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null || !username.equals(user.getUsername())) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.getStatus());
    }
}
