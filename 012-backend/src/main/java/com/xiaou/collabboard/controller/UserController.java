package com.xiaou.collabboard.controller;

import com.xiaou.collabboard.entity.User;
import com.xiaou.collabboard.service.UserService;
import com.xiaou.collabboard.util.FileUtil;
import com.xiaou.collabboard.util.Result;
import com.xiaou.collabboard.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileUtil fileUtil;

    @GetMapping("/profile")
    public Result<User> getProfile() {
        Long userId = UserHolder.get();
        User user = userService.getById(userId);
        user.setPassword(null);
        return Result.success(user);
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return Result.success(user);
    }

    @PutMapping("/profile")
    public Result<Void> updateProfile(@RequestBody Map<String, String> params) {
        Long userId = UserHolder.get();
        User user = userService.getById(userId);

        if (params.containsKey("nickname")) {
            user.setNickname(params.get("nickname"));
        }
        if (params.containsKey("email")) {
            user.setEmail(params.get("email"));
        }
        if (params.containsKey("phone")) {
            user.setPhone(params.get("phone"));
        }
        if (params.containsKey("signature")) {
            user.setSignature(params.get("signature"));
        }

        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);

        return Result.success();
    }

    @PostMapping("/avatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            String filename = fileUtil.saveAvatar(file);
            Long userId = UserHolder.get();
            User user = userService.getById(userId);
            user.setAvatar(filename);
            userService.updateById(user);
            return Result.success(filename);
        } catch (Exception e) {
            return Result.error("上传失败: " + e.getMessage());
        }
    }

    @PutMapping("/password")
    public Result<Void> updatePassword(@RequestBody Map<String, String> params) {
        Long userId = UserHolder.get();
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");

        userService.updatePassword(userId, oldPassword, newPassword);

        return Result.success();
    }

    @GetMapping("/stats")
    public Result<Map<String, Object>> getUserStats() {
        Long userId = UserHolder.get();
        User user = userService.getById(userId);

        Map<String, Object> stats = Map.of(
            "docCount", user.getDocCount(),
            "collabCount", user.getCollabCount(),
            "teamCount", user.getTeamCount()
        );

        return Result.success(stats);
    }
}

