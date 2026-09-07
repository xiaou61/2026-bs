package com.milk.controller;

import com.milk.common.Result;
import com.milk.entity.MilkProduct;
import com.milk.service.MilkProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Resource
    private MilkProductService milkProductService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(required = false) Long categoryId) {
        return Result.success(milkProductService.list(categoryId));
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Long categoryId) {
        return Result.success(milkProductService.page(pageNum, pageSize, name, categoryId));
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(milkProductService.getById(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody MilkProduct product) {
        milkProductService.save(product);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody MilkProduct product) {
        milkProductService.save(product);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        milkProductService.deleteById(id);
        return Result.success();
    }
}
