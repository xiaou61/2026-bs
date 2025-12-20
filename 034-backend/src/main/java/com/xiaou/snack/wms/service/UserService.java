package com.xiaou.snack.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.snack.wms.entity.basic.User;

public interface UserService extends IService<User> {
    String login(String username, String password);
}
