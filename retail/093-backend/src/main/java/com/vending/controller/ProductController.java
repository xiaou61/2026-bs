package com.vending.controller;

import com.vending.common.Result;
import com.vending.entity.ProductInfo;
import com.vending.service.ProductService;
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
@RequestMapping("/api/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @GetMapping("/public/list")
    public Result<?> publicList() {
        return Result.success(productService.publicList());
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Long categoryId,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        return Result.success(productService.page(pageNum, pageSize, name, categoryId, status));
    }

    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(productService.listAll());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody ProductInfo product, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        productService.save(product);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        productService.deleteById(id);
        return Result.success();
    }
}
