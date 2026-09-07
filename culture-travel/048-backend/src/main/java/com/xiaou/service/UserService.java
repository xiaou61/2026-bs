package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.LoginDTO;
import com.xiaou.dto.RegisterDTO;
import com.xiaou.entity.User;
import com.xiaou.vo.LoginVO;

public interface UserService {
    LoginVO login(LoginDTO dto);
    void register(RegisterDTO dto);
    User getById(Long id);
    Page<User> page(int current, int size, String keyword, Integer role);
    void updateStatus(Long id, Integer status);
}
