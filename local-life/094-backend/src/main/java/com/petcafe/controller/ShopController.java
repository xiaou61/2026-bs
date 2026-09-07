package com.petcafe.controller;

import com.petcafe.common.Result;
import com.petcafe.entity.CafeShop;
import com.petcafe.service.ShopService;
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
@RequestMapping("/api/shop")
public class ShopController {

    @Resource
    private ShopService shopService;

    @GetMapping("/public/list")
    public Result<?> publicList() {
        return Result.success(shopService.publicList());
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) String status,
                          @RequestParam(required = false) Long areaId,
                          HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(shopService.page(pageNum, pageSize, name, status, areaId));
    }

    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(shopService.listAll());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody CafeShop shop, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        shopService.save(shop);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        shopService.deleteById(id);
        return Result.success();
    }
}
