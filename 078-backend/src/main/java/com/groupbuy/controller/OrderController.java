package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.entity.Merchant;
import com.groupbuy.service.MerchantService;
import com.groupbuy.service.OrderService;
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
        Long userId = (Long) request.getAttribute("userId");
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
    public Result<?> detail(@PathVariable Long id) {
        return Result.success(orderService.detail(id));
    }

    @PutMapping("/pay/{id}")
    public Result<?> pay(@PathVariable Long id) {
        orderService.pay(id);
        return Result.success();
    }

    @PutMapping("/ship/{id}")
    public Result<?> ship(@PathVariable Long id) {
        orderService.ship(id);
        return Result.success();
    }

    @PutMapping("/receive/{id}")
    public Result<?> receive(@PathVariable Long id) {
        orderService.receive(id);
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    public Result<?> cancel(@PathVariable Long id) {
        orderService.cancel(id);
        return Result.success();
    }

    @PutMapping("/refund/{id}")
    public Result<?> applyRefund(@PathVariable Long id, @RequestBody Map<String, String> params) {
        orderService.applyRefund(id, params.get("reason"));
        return Result.success();
    }

    @PutMapping("/refund/handle/{id}")
    public Result<?> handleRefund(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        Boolean agree = (Boolean) params.get("agree");
        String remark = (String) params.get("remark");
        orderService.handleRefund(id, agree, remark);
        return Result.success();
    }
}
