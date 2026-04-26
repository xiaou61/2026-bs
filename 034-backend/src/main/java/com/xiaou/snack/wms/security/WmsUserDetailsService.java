package com.xiaou.snack.wms.security;

import com.xiaou.snack.wms.entity.basic.User;
import com.xiaou.snack.wms.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WmsUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public WmsUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.lambdaQuery().eq(User::getUsername, username).one();
        if (user == null) {
            throw new UsernameNotFoundException("user not found: " + username);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoleId() != null ? user.getRoleId().toString() : "user")
                .build();
    }
}
