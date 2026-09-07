package com.xiaou.controller;

import com.xiaou.common.R;
import com.xiaou.entity.User;
import com.xiaou.service.FriendGroupService;
import com.xiaou.service.UserService;
import com.xiaou.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private FriendGroupService friendGroupService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/register")
    public R register(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return R.error("用户名和密码不能为空");
        }
        
        User existUser = userService.getByUsername(user.getUsername());
        if (existUser != null) {
            return R.error("用户名已存在");
        }
        
        if (user.getNickname() == null) {
            user.setNickname(user.getUsername());
        }
        
        user.setStatus(1);
        userService.save(user);
        
        friendGroupService.createDefaultGroup(user.getId());
        
        return R.ok("注册成功");
    }
    
    @PostMapping("/login")
    public R login(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return R.error("用户名和密码不能为空");
        }
        
        User existUser = userService.getByUsername(user.getUsername());
        if (existUser == null) {
            return R.error("用户不存在");
        }
        
        if (!existUser.getPassword().equals(user.getPassword())) {
            return R.error("密码错误");
        }
        
        String token = jwtUtil.generateToken(existUser.getId());
        
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        existUser.setPassword(null);
        data.put("user", existUser);
        
        return R.ok(data);
    }
}

