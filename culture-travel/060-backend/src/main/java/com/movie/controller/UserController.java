package com.movie.controller;

import com.movie.common.Result;
import com.movie.entity.User;
import com.movie.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String username) {
        return Result.success(userService.getPage(pageNum, pageSize, username));
    }

    @PutMapping
    public Result<?> update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody User user) {
        userService.updateStatus(user.getId(), user.getStatus());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return Result.success();
    }
}
