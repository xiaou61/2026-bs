package com.oa.controller;

import com.oa.common.Result;
import com.oa.entity.User;
import com.oa.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> params) {
        String token = userService.login(params.get("username"), params.get("password"));
        return Result.success(token);
    }

    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(userService.getInfo(userId));
    }

    @PutMapping("/password")
    public Result updatePassword(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Long userId = (Long) request.getAttribute("userId");
        userService.updatePassword(userId, params.get("oldPassword"), params.get("newPassword"));
        return Result.success();
    }

    @PutMapping("/profile")
    public Result updateProfile(HttpServletRequest request, @RequestBody User user) {
        Long userId = (Long) request.getAttribute("userId");
        user.setId(userId);
        userService.updateProfile(user);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String keyword, Long deptId) {
        return Result.success(userService.getList(pageNum, pageSize, keyword, deptId));
    }

    @GetMapping("/all")
    public Result all() {
        return Result.success(userService.getAll());
    }

    @PostMapping
    public Result add(@RequestBody User user) {
        userService.add(user);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        userService.delete(id);
        return Result.success();
    }
}
