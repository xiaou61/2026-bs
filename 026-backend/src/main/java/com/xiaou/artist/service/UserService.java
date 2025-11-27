package com.xiaou.artist.service;

import com.xiaou.artist.entity.User;
import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    User login(String username, String password);
    User register(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    boolean updateUser(User user);
    boolean updatePassword(Long id, String password);
    boolean updateBalance(Long id, BigDecimal amount);
}
