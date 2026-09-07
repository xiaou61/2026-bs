package com.xiaou.wedding.controller;

import com.xiaou.wedding.common.Result;
import com.xiaou.wedding.entity.Package;
import com.xiaou.wedding.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/package")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping("/list")
    public Result<List<Package>> list(@RequestParam(required = false) String category) {
        return Result.success(packageService.list(category));
    }

    @GetMapping("/{id}")
    public Result<Package> detail(@PathVariable Long id) {
        return Result.success(packageService.detail(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody Package pkg) {
        packageService.create(pkg);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Package pkg) {
        packageService.update(pkg);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        packageService.delete(id);
        return Result.success();
    }
}
