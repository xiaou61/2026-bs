package com.xiaou.rice.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.rice.modules.user.entity.User;
import com.xiaou.rice.modules.user.mapper.UserMapper;
import com.xiaou.rice.modules.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}