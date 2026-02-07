package com.milk.controller;

import com.milk.common.Result;
import com.milk.entity.DeliveryRoute;
import com.milk.service.DeliveryRouteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Resource
    private DeliveryRouteService deliveryRouteService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(deliveryRouteService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name) {
        return Result.success(deliveryRouteService.page(pageNum, pageSize, name));
    }

    @PostMapping
    public Result<?> add(@RequestBody DeliveryRoute route) {
        deliveryRouteService.save(route);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody DeliveryRoute route) {
        deliveryRouteService.save(route);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        deliveryRouteService.deleteById(id);
        return Result.success();
    }
}
