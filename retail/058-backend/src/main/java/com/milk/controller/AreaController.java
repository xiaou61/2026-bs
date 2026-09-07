package com.milk.controller;

import com.milk.common.Result;
import com.milk.entity.DeliveryArea;
import com.milk.service.DeliveryAreaService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/area")
public class AreaController {

    @Resource
    private DeliveryAreaService deliveryAreaService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(deliveryAreaService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name) {
        return Result.success(deliveryAreaService.page(pageNum, pageSize, name));
    }

    @PostMapping
    public Result<?> add(@RequestBody DeliveryArea area) {
        deliveryAreaService.save(area);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody DeliveryArea area) {
        deliveryAreaService.save(area);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        deliveryAreaService.deleteById(id);
        return Result.success();
    }
}
