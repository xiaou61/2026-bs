package com.vending.controller;

import com.vending.common.Result;
import com.vending.entity.MachineLocation;
import com.vending.service.LocationService;
import com.vending.utils.AuthUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Resource
    private LocationService locationService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(locationService.page(pageNum, pageSize, name, status));
    }

    @GetMapping("/all")
    public Result<?> all(HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(locationService.listAll());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody MachineLocation location, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        locationService.save(location);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        locationService.deleteById(id);
        return Result.success();
    }
}
