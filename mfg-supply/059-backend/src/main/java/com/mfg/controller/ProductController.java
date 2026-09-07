package com.mfg.controller;

import com.mfg.common.Result;
import com.mfg.entity.Product;
import com.mfg.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name) {
        return Result.success(productService.page(pageNum, pageSize, name));
    }

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(productService.list());
    }

    @PostMapping
    public Result<?> add(@RequestBody Product product) {
        productService.add(product);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Product product) {
        productService.update(product);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return Result.success();
    }
}
