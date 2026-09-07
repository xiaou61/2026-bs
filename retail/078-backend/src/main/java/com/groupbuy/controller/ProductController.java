package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.entity.Merchant;
import com.groupbuy.entity.Product;
import com.groupbuy.service.MerchantService;
import com.groupbuy.service.ProductService;
import com.groupbuy.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private MerchantService merchantService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Long categoryId, String name, Integer status, Long merchantId,
                          HttpServletRequest request) {
        Integer role = (Integer) request.getAttribute("role");
        if (role != null && role == 1) {
            Long userId = (Long) request.getAttribute("userId");
            Merchant merchant = merchantService.getByUserId(userId);
            if (merchant != null) {
                merchantId = merchant.getId();
            }
        }
        return Result.success(productService.page(pageNum, pageSize, categoryId, name, status, merchantId));
    }

    @GetMapping("/detail/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return Result.success(productService.detail(id));
    }

    @PostMapping("/add")
    public Result<?> add(HttpServletRequest request, @RequestBody Product product) {
        AuthUtils.requireMerchant(request);
        Long userId = AuthUtils.getUserId(request);
        Merchant merchant = merchantService.requireApprovedMerchant(userId);
        productService.add(merchant.getId(), product);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> update(HttpServletRequest request, @RequestBody Product product) {
        AuthUtils.requireMerchant(request);
        Merchant merchant = merchantService.requireApprovedMerchant(AuthUtils.getUserId(request));
        productService.update(merchant.getId(), product);
        return Result.success();
    }

    @PutMapping("/status/{id}")
    public Result<?> updateStatus(HttpServletRequest request, @PathVariable Long id, @RequestBody Map<String, Integer> params) {
        AuthUtils.requireMerchant(request);
        Merchant merchant = merchantService.requireApprovedMerchant(AuthUtils.getUserId(request));
        productService.updateStatus(merchant.getId(), id, params.get("status"));
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(HttpServletRequest request, @PathVariable Long id) {
        AuthUtils.requireMerchant(request);
        Merchant merchant = merchantService.requireApprovedMerchant(AuthUtils.getUserId(request));
        productService.delete(merchant.getId(), id);
        return Result.success();
    }
}
