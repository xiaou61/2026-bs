package com.eldercare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.common.Result;
import com.eldercare.entity.ElderProfile;
import com.eldercare.service.ElderService;
import com.eldercare.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/elder")
public class ElderController {

    @Autowired
    private ElderService elderService;

    @GetMapping("/list")
    public Result<Page<ElderProfile>> list(@RequestParam(defaultValue = "1") int pageNum,
                                           @RequestParam(defaultValue = "10") int pageSize,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String phone,
                                           @RequestParam(required = false) Integer status,
                                           HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "doctor", "nurse", "reception");
        return Result.success(elderService.page(pageNum, pageSize, name, phone, status));
    }

    @GetMapping("/all")
    public Result<List<ElderProfile>> all(HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "doctor", "nurse", "reception");
        return Result.success(elderService.listAll());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody ElderProfile elderProfile, HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "reception");
        elderService.add(elderProfile);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody ElderProfile elderProfile, HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "reception");
        elderService.update(elderProfile);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "reception");
        elderService.delete(id);
        return Result.success();
    }
}
