package com.xiaou.community.service;

import com.xiaou.community.entity.User;
import java.util.List;

public interface UserService {
    User login(String username, String password);
    void register(User user);
    User getById(Integer id);
    void updatePassword(Integer id, String newPassword);
    List<User> getAll();
}
