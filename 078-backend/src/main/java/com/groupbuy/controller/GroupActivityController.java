package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.entity.GroupActivity;
import com.groupbuy.entity.Merchant;
import com.groupbuy.service.GroupActivityService;
import com.groupbuy.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/activity")
public class GroupActivityController {

    @Autowired
    private GroupActivityService groupActivityService;

    @Autowired
    private MerchantService merchantService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Long productId, Integer status,
                          HttpServletRequest request) {
        Long merchantId = null;
        Integer role = (Integer) request.getAttribute("role");
        if (role != null && role == 1) {
            Long userId = (Long) request.getAttribute("userId");
            Merchant merchant = merchantService.getByUserId(userId);
            if (merchant != null) {
                merchantId = merchant.getId();
            }
        }
        return Result.success(groupActivityService.page(pageNum, pageSize, productId, status, merchantId));
    }

    @GetMapping("/detail/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return Result.success(groupActivityService.detail(id));
    }

    @PostMapping("/add")
    public Result<?> add(HttpServletRequest request, @RequestBody GroupActivity activity) {
        Long userId = (Long) request.getAttribute("userId");
        Merchant merchant = merchantService.getByUserId(userId);
        groupActivityService.add(merchant.getId(), activity);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody GroupActivity activity) {
        groupActivityService.update(activity);
        return Result.success();
    }

    @PutMapping("/end/{id}")
    public Result<?> end(@PathVariable Long id) {
        groupActivityService.end(id);
        return Result.success();
    }

    @GetMapping("/home")
    public Result<?> home(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Long categoryId) {
        return Result.success(groupActivityService.home(pageNum, pageSize, categoryId));
    }
}
