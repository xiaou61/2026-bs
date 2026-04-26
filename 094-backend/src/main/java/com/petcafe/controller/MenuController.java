package com.petcafe.controller;

import com.petcafe.common.Result;
import com.petcafe.entity.MenuItem;
import com.petcafe.service.MenuService;
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
@RequestMapping("/api/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/public/list")
    public Result<?> publicList() {
        return Result.success(menuService.publicList());
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Long categoryId,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(menuService.page(pageNum, pageSize, name, categoryId, status));
    }

    @GetMapping("/all")
    public Result<?> all(HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(menuService.listAll());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody MenuItem menu, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        menuService.save(menu);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        menuService.deleteById(id);
        return Result.success();
    }
}
