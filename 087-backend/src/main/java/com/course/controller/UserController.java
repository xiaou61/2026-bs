package com.course.controller;

import com.course.common.Result;
import com.course.entity.SysUser;
import com.course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/options")
    public Result<List<SysUser>> options(@RequestParam String role, @RequestAttribute("role") String currentRole) {
        return Result.success(userService.options(role, currentRole));
    }
}
