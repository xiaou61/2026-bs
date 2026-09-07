package com.adoption.controller;

import com.adoption.common.Result;
import com.adoption.entity.AdoptionApplication;
import com.adoption.entity.ApplicationMaterial;
import com.adoption.service.ApplicationService;
import com.adoption.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private AuthService authService;

    @GetMapping("/list")
    public Result<Map<String, Object>> list(@RequestAttribute("role") String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) Integer status) {
        authService.assertStaff(role);
        return Result.success(applicationService.page(pageNum, pageSize, status));
    }

    @GetMapping("/my/list")
    public Result<Map<String, Object>> myList(@RequestAttribute("userId") Long userId,
                                              @RequestAttribute("role") String role,
                                              @RequestParam(required = false) Integer pageNum,
                                              @RequestParam(required = false) Integer pageSize) {
        authService.assertApplicant(role);
        return Result.success(applicationService.myPage(userId, pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestAttribute("userId") Long userId,
                              @RequestAttribute("role") String role,
                              @RequestBody AdoptionApplication application) {
        authService.assertApplicant(role);
        applicationService.add(userId, application);
        return Result.success();
    }

    @PutMapping("/review")
    public Result<String> review(@RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role,
                                 @RequestParam Long applicationId,
                                 @RequestParam Integer reviewStatus,
                                 @RequestParam(required = false) String reviewRemark) {
        authService.assertStaff(role);
        applicationService.review(userId, applicationId, reviewStatus, reviewRemark);
        return Result.success();
    }

    @GetMapping("/material/list")
    public Result<List<ApplicationMaterial>> materialList(@RequestAttribute("userId") Long userId,
                                                          @RequestAttribute("role") String role,
                                                          @RequestParam Long applicationId) {
        return Result.success(applicationService.getMaterialList(userId, role, applicationId));
    }

    @PostMapping("/material/add")
    public Result<String> addMaterial(@RequestAttribute("userId") Long userId,
                                      @RequestAttribute("role") String role,
                                      @RequestBody ApplicationMaterial material) {
        applicationService.addMaterial(userId, role, material);
        return Result.success();
    }
}
