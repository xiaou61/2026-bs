package com.harbin.tourism.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.Result;
import com.harbin.tourism.entity.Restaurant;
import com.harbin.tourism.service.RestaurantService;
import com.harbin.tourism.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/list")
    public Result<Page<Restaurant>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String cuisineType) {
        return Result.success(restaurantService.page(pageNum, pageSize, keyword, cuisineType));
    }

    @GetMapping("/{id}")
    public Result<Restaurant> getById(@PathVariable Long id) {
        return Result.success(restaurantService.getById(id));
    }

    @GetMapping("/all")
    public Result<List<Restaurant>> all() {
        return Result.success(restaurantService.listAll());
    }

    @PostMapping
    public Result<Void> add(@RequestBody Restaurant restaurant, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        restaurantService.save(restaurant);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Restaurant restaurant, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        restaurantService.update(restaurant);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        restaurantService.delete(id);
        return Result.success();
    }
}
