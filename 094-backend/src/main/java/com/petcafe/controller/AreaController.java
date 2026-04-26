package com.petcafe.controller;

import com.petcafe.common.Result;
import com.petcafe.entity.CafeArea;
import com.petcafe.service.AreaService;
import com.petcafe.utils.AuthUtils;
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
@RequestMapping("/api/area")
public class AreaController {

    @Resource
    private AreaService areaService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(areaService.page(pageNum, pageSize, name, status));
    }

    @GetMapping("/all")
    public Result<?> all(HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(areaService.listAll());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody CafeArea area, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        areaService.save(area);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        areaService.deleteById(id);
        return Result.success();
    }
}
