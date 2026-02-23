package com.inventory.controller;

import com.inventory.common.BusinessException;
import com.inventory.common.Result;
import com.inventory.entity.Product;
import com.inventory.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(productService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) String productNo,
                          @RequestParam(required = false) Long categoryId,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        return Result.success(productService.page(pageNum, pageSize, name, productNo, categoryId, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody Product product, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        productService.save(product);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Product product, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        productService.save(product);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        productService.deleteById(id);
        return Result.success();
    }

    private void checkStaffRole(String role) {
        if (!"ADMIN".equals(role) && !"STAFF".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
