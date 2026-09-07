package com.property.controller;

import com.property.common.BusinessException;
import com.property.common.Result;
import com.property.entity.House;
import com.property.service.HouseService;
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
@RequestMapping("/api/house")
public class HouseController {

    @Resource
    private HouseService houseService;

    @GetMapping("/list")
    public Result<?> list(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if ("OWNER".equals(role)) {
            return Result.success(houseService.listByOwner((Long) request.getAttribute("userId")));
        }
        return Result.success(houseService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long buildingId,
                          @RequestParam(required = false) Long ownerId,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) String keyword,
                          HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        return Result.success(houseService.page(pageNum, pageSize, buildingId, ownerId, status, keyword));
    }

    @PostMapping
    public Result<?> add(@RequestBody House house, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        houseService.save(house);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody House house, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        houseService.save(house);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        houseService.deleteById(id);
        return Result.success();
    }

    private void checkStaffRole(String role) {
        if (!"ADMIN".equals(role) && !"STAFF".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
