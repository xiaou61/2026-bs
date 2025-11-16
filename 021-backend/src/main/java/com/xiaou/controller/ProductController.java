package com.xiaou.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.PageResult;
import com.xiaou.common.Result;
import com.xiaou.dto.ProductPublishDTO;
import com.xiaou.dto.ProductQueryDTO;
import com.xiaou.service.ProductService;
import com.xiaou.vo.ProductDetailVO;
import com.xiaou.vo.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/publish")
    public Result<Long> publishProduct(@RequestAttribute("userId") Long userId,
                                     @Valid @RequestBody ProductPublishDTO publishDTO) {
        return Result.success(productService.publishProduct(userId, publishDTO));
    }

    @PutMapping("/update")
    public Result<String> updateProduct(@RequestAttribute("userId") Long userId,
                                       @RequestParam Long productId,
                                       @Valid @RequestBody ProductPublishDTO updateDTO) {
        productService.updateProduct(userId, productId, updateDTO);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteProduct(@RequestAttribute("userId") Long userId,
                                       @PathVariable Long id) {
        productService.deleteProduct(userId, id);
        return Result.success("删除成功");
    }

    @PutMapping("/{id}/shelf")
    public Result<String> changeProductStatus(@RequestAttribute("userId") Long userId,
                                             @PathVariable Long id,
                                             @RequestParam String status) {
        productService.changeProductStatus(userId, id, status);
        return Result.success("状态更新成功");
    }

    @GetMapping("/list")
    public Result<PageResult<ProductVO>> getProductList(ProductQueryDTO queryDTO) {
        Page<ProductVO> page = productService.getProductList(queryDTO);
        return Result.success(PageResult.of(page.getTotal(), page.getRecords(), page.getCurrent(), page.getSize()));
    }

    @GetMapping("/{id}")
    public Result<ProductDetailVO> getProductDetail(@PathVariable Long id) {
        return Result.success(productService.getProductDetail(id));
    }

    @GetMapping("/my")
    public Result<PageResult<ProductVO>> getMyProducts(@RequestAttribute("userId") Long userId,
                                                      @RequestParam(defaultValue = "1") Integer current,
                                                      @RequestParam(defaultValue = "10") Integer size) {
        Page<ProductVO> page = productService.getMyProducts(userId, current, size);
        return Result.success(PageResult.of(page.getTotal(), page.getRecords(), page.getCurrent(), page.getSize()));
    }
}