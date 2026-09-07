package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.entity.Merchant;
import com.groupbuy.service.MerchantService;
import com.groupbuy.service.OrderService;
import com.groupbuy.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MerchantService merchantService;

    @PostMapping("/create")
    public Result<?> create(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        AuthUtils.requireUser(request);
        Long userId = AuthUtils.getUserId(request);
        List<Long> cartIds = (List<Long>) params.get("cartIds");
        Long addressId = Long.valueOf(params.get("addressId").toString());
        String remark = (String) params.get("remark");
        return Result.success(orderService.create(userId, cartIds, addressId, remark));
    }

    @GetMapping("/page")
    public Result<?> page(HttpServletRequest request,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Integer status, String orderNo) {
        Long userId = (Long) request.getAttribute("userId");
        Integer role = (Integer) request.getAttribute("role");
        Long merchantId = null;
        if (role == 1) {
            Merchant merchant = merchantService.getByUserId(userId);
            if (merchant != null) {
                merchantId = merchant.getId();
            }
        }
        return Result.success(orderService.page(userId, role, merchantId, pageNum, pageSize, status, orderNo));
    }

    @GetMapping("/detail/{id}")
    public Result<?> detail(HttpServletRequest request, @PathVariable Long id) {
        Long userId = AuthUtils.getUserId(request);
        Integer role = AuthUtils.getRole(request);
        Long merchantId = getMerchantId(userId, role);
        return Result.success(orderService.detail(id, userId, role, merchantId));
    }

    @PutMapping("/pay/{id}")
    public Result<?> pay(HttpServletRequest request, @PathVariable Long id) {
        AuthUtils.requireUser(request);
        orderService.pay(id, AuthUtils.getUserId(request));
        return Result.success();
    }

    @PutMapping("/ship/{id}")
    public Result<?> ship(HttpServletRequest request, @PathVariable Long id) {
        AuthUtils.requireMerchant(request);
        Merchant merchant = merchantService.requireApprovedMerchant(AuthUtils.getUserId(request));
        orderService.ship(id, merchant.getId());
        return Result.success();
    }

    @PutMapping("/receive/{id}")
    public Result<?> receive(HttpServletRequest request, @PathVariable Long id) {
        AuthUtils.requireUser(request);
        orderService.receive(id, AuthUtils.getUserId(request));
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    public Result<?> cancel(HttpServletRequest request, @PathVariable Long id) {
        AuthUtils.requireUser(request);
        orderService.cancel(id, AuthUtils.getUserId(request));
        return Result.success();
    }

    @PutMapping("/refund/{id}")
    public Result<?> applyRefund(HttpServletRequest request, @PathVariable Long id, @RequestBody Map<String, String> params) {
        AuthUtils.requireUser(request);
        orderService.applyRefund(id, AuthUtils.getUserId(request), params.get("reason"));
        return Result.success();
    }

    @PutMapping("/refund/handle/{id}")
    public Result<?> handleRefund(HttpServletRequest request, @PathVariable Long id, @RequestBody Map<String, Object> params) {
        AuthUtils.requireMerchant(request);
        Merchant merchant = merchantService.requireApprovedMerchant(AuthUtils.getUserId(request));
        Boolean agree = (Boolean) params.get("agree");
        String remark = (String) params.get("remark");
        orderService.handleRefund(id, merchant.getId(), agree, remark);
        return Result.success();
    }

    private Long getMerchantId(Long userId, Integer role) {
        if (role != null && role == 1) {
            Merchant merchant = merchantService.getByUserId(userId);
            return merchant == null ? null : merchant.getId();
        }
        return null;
    }
}
