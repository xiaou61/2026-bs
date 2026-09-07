package com.xiaou.controller;

import com.xiaou.common.CurrentUser;
import com.xiaou.common.R;
import com.xiaou.entity.User;
import com.xiaou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/info")
    public R getUserInfo() {
        Long userId = CurrentUser.get();
        User user = userService.getById(userId);
        if (user != null) {
            user.setPassword(null);
            return R.ok(user);
        }
        return R.error("用户不存在");
    }
    
    @PutMapping("/update")
    public R updateUserInfo(@RequestBody User user) {
        Long userId = CurrentUser.get();
        user.setId(userId);
        user.setPassword(null);
        user.setUsername(null);
        userService.updateById(user);
        return R.ok("更新成功");
    }
    
    @GetMapping("/search")
    public R searchUsers(@RequestParam String keyword) {
        return R.ok(userService.searchUsers(keyword));
    }
}

