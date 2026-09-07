package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.entity.Merchant;
import com.groupbuy.service.MerchantService;
import com.groupbuy.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @PostMapping("/apply")
    public Result<?> apply(HttpServletRequest request, @RequestBody Merchant merchant) {
        AuthUtils.requireUser(request);
        Long userId = AuthUtils.getUserId(request);
        merchantService.apply(userId, merchant);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          String name, Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        return Result.success(merchantService.page(pageNum, pageSize, name, status));
    }

    @PutMapping("/audit/{id}")
    public Result<?> audit(HttpServletRequest request, @PathVariable Long id, @RequestBody Map<String, Object> params) {
        AuthUtils.requireAdmin(request);
        Integer status = (Integer) params.get("status");
        String auditRemark = (String) params.get("auditRemark");
        merchantService.audit(id, status, auditRemark);
        return Result.success();
    }

    @GetMapping("/info")
    public Result<?> info(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(merchantService.getByUserId(userId));
    }

    @PutMapping("/update")
    public Result<?> update(HttpServletRequest request, @RequestBody Merchant merchant) {
        Long userId = (Long) request.getAttribute("userId");
        merchantService.update(userId, merchant);
        return Result.success();
    }
}
