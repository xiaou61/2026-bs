package com.adoption.controller;

import com.adoption.common.Result;
import com.adoption.entity.ChildProfile;
import com.adoption.service.AuthService;
import com.adoption.service.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/child")
public class ChildController {

    @Autowired
    private ChildService childService;

    @Autowired
    private AuthService authService;

    @GetMapping("/list")
    public Result<Map<String, Object>> list(@RequestAttribute("role") String role,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) Integer adoptionStatus) {
        authService.assertStaff(role);
        return Result.success(childService.page(pageNum, pageSize, name, adoptionStatus));
    }

    @GetMapping("/public/list")
    public Result<Map<String, Object>> publicList(@RequestParam(required = false) Integer pageNum,
                                                  @RequestParam(required = false) Integer pageSize,
                                                  @RequestParam(required = false) String name,
                                                  @RequestParam(required = false) Integer gender,
                                                  @RequestParam(required = false) Integer ageMin,
                                                  @RequestParam(required = false) Integer ageMax) {
        return Result.success(childService.publicPage(pageNum, pageSize, name, gender, ageMin, ageMax));
    }

    @GetMapping("/detail/{id}")
    public Result<ChildProfile> detail(@PathVariable Long id) {
        return Result.success(childService.detail(id));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestAttribute("role") String role, @RequestBody ChildProfile child) {
        authService.assertStaff(role);
        childService.add(child);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestAttribute("role") String role, @RequestBody ChildProfile child) {
        authService.assertStaff(role);
        childService.update(child);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        childService.delete(id);
        return Result.success();
    }
}
