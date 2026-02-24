package com.hrm.controller;

import com.hrm.common.Result;
import com.hrm.entity.User;
import com.hrm.service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> params) {
        return Result.success(userService.login(params.get("username"), params.get("password")));
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        userService.logout(userId);
        return Result.success();
    }

    @GetMapping("/info")
    public Result getInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(userService.getById(userId));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(userService.getById(id));
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) String username,
                          @RequestParam(required = false) String role,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(userService.getList(username, role, pageNum, pageSize));
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

    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        String newPassword = params.get("newPassword").toString();
        userService.resetPassword(id, newPassword);
        return Result.success();
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        userService.updatePassword(userId, params.get("oldPassword"), params.get("newPassword"));
        return Result.success();
    }
}
