package com.xiaou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.User;
import com.xiaou.dto.LoginDTO;
import com.xiaou.dto.RegisterDTO;
import java.util.Map;

public interface UserService extends IService<User> {
    Map<String, Object> login(LoginDTO dto);
    void register(RegisterDTO dto);
    User getByUsername(String username);
    Page<User> getPage(int current, int size, String keyword, Integer role);
    void updateProfile(User user);
    Map<String, Object> getStudyStats(Long userId);
}
