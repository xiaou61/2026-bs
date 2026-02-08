package com.mfg.controller;

import com.mfg.common.Result;
import com.mfg.entity.User;
import com.mfg.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String username,
                          @RequestParam(required = false) String role) {
        return Result.success(userService.page(pageNum, pageSize, username, role));
    }

    @GetMapping("/info")
    public Result<?> info(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getAttribute("userId").toString());
        return Result.success(userService.getUserInfo(userId));
    }

    @PostMapping
    public Result<?> add(@RequestBody User user) {
        userService.add(user);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return Result.success();
    }
}
