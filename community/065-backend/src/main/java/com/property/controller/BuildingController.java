package com.property.controller;

import com.property.common.BusinessException;
import com.property.common.Result;
import com.property.entity.Building;
import com.property.service.BuildingService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/building")
public class BuildingController {

    @Resource
    private BuildingService buildingService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(buildingService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        return Result.success(buildingService.page(pageNum, pageSize, name, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody Building building, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        buildingService.save(building);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Building building, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        buildingService.save(building);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        buildingService.deleteById(id);
        return Result.success();
    }

    private void checkStaffRole(String role) {
        if (!"ADMIN".equals(role) && !"STAFF".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
