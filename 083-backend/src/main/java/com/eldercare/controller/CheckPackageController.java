package com.eldercare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.common.Result;
import com.eldercare.entity.CheckPackage;
import com.eldercare.service.CheckPackageService;
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
@RequestMapping("/api/package")
public class CheckPackageController {

    @Autowired
    private CheckPackageService checkPackageService;

    @GetMapping("/list")
    public Result<Page<CheckPackage>> list(@RequestParam(defaultValue = "1") int pageNum,
                                           @RequestParam(defaultValue = "10") int pageSize,
                                           @RequestParam(required = false) String packageName,
                                           @RequestParam(required = false) Integer status,
                                           HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "doctor", "nurse", "reception");
        return Result.success(checkPackageService.page(pageNum, pageSize, packageName, status));
    }

    @GetMapping("/all")
    public Result<List<CheckPackage>> all(HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "doctor", "nurse", "reception");
        return Result.success(checkPackageService.listAll());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody CheckPackage checkPackage, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        checkPackageService.add(checkPackage);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody CheckPackage checkPackage, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        checkPackageService.update(checkPackage);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        checkPackageService.delete(id);
        return Result.success();
    }
}
