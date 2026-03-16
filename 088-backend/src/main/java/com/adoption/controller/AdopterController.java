package com.adoption.controller;

import com.adoption.common.Result;
import com.adoption.service.AdopterService;
import com.adoption.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/adopter")
public class AdopterController {

    @Autowired
    private AdopterService adopterService;

    @Autowired
    private AuthService authService;

    @GetMapping("/list")
    public Result<Map<String, Object>> list(@RequestAttribute("role") String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize) {
        authService.assertStaff(role);
        return Result.success(adopterService.page(pageNum, pageSize));
    }
}
