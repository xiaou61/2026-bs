package com.opera.controller;

import com.opera.common.Result;
import com.opera.entity.SysUser;
import com.opera.service.UserService;
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


