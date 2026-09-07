package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.common.BusinessException;
import com.xiaou.common.Result;
import com.xiaou.entity.Product;
import com.xiaou.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "12") Integer size,
                          @RequestParam(required = false) String keyword,
                          @RequestParam(required = false) Long categoryId,
                          @RequestParam(required = false) String sortBy) {
        IPage<Product> result = productService.searchProducts(page, size, keyword, categoryId, "approved", sortBy);
        return Result.success(result);
    }

    @GetMapping("/detail/{id}")
    public Result<?> detail(@PathVariable Long id) {
        Product product = productService.getById(id);
        return Result.success(product);
    }

    @GetMapping("/my-products")
    public Result<?> myProducts(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer size,
                                @RequestAttribute Long userId) {
        IPage<Product> result = productService.searchProducts(page, size, null, null, null, null);
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getFarmerId, userId);
        wrapper.orderByDesc(Product::getCreateTime);
        return Result.success(productService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page, size), wrapper));
    }

    @PostMapping
    public Result<?> create(@RequestBody Product product, @RequestAttribute Long userId, @RequestAttribute String userRole) {
        if (!"farmer".equals(userRole) && !"admin".equals(userRole)) {
            throw new BusinessException(403, "只有农户可以发布商品");
        }
        product.setFarmerId(userId);
        productService.publishProduct(product);
        return Result.success("发布成功，等待审核");
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Product product, @RequestAttribute Long userId) {
        product.setId(id);
        productService.updateProduct(product, userId);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, @RequestAttribute Long userId) {
        productService.deleteProduct(id, userId);
        return Result.success("删除成功");
    }

    @GetMapping("/pending")
    public Result<?> pending(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size,
                             @RequestAttribute String userRole) {
        if (!"admin".equals(userRole)) {
            throw new BusinessException(403, "无权访问");
        }
        IPage<Product> result = productService.searchProducts(page, size, null, null, "pending", null);
        return Result.success(result);
    }

    @PutMapping("/{id}/approve")
    public Result<?> approve(@PathVariable Long id,
                             @RequestBody java.util.Map<String, String> params,
                             @RequestAttribute String userRole) {
        if (!"admin".equals(userRole)) {
            throw new BusinessException(403, "无权访问");
        }
        String status = params.get("status");
        productService.approveProduct(id, status);
        return Result.success("审核成功");
    }
}

