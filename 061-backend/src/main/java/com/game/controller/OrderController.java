package com.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.game.common.BusinessException;
import com.game.common.Result;
import com.game.entity.TradeOrder;
import com.game.service.TradeOrderService;
import com.game.vo.TradeOrderVO;
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
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private TradeOrderService orderService;

    @PostMapping
    public Result<?> create(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (params.get("itemId") == null || params.get("quantity") == null) {
            throw new BusinessException("缺少下单参数");
        }
        Long itemId = ((Number) params.get("itemId")).longValue();
        Integer quantity = ((Number) params.get("quantity")).intValue();
        String remark = params.get("remark") == null ? null : params.get("remark").toString();
        TradeOrder order = orderService.create(userId, itemId, quantity, remark);
        return Result.success(order);
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String orderNo,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) Long userId,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        Page<TradeOrderVO> page = orderService.page(pageNum, pageSize, orderNo, status, userId);
        return Result.success(page);
    }

    @GetMapping("/my")
    public Result<?> my(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) Integer status,
                        @RequestParam(required = false) String orderNo,
                        HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Page<TradeOrderVO> page = orderService.myOrders(userId, pageNum, pageSize, status, orderNo);
        return Result.success(page);
    }

    @GetMapping("/sale")
    public Result<?> sale(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) String orderNo,
                          HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Page<TradeOrderVO> page = orderService.saleOrders(userId, pageNum, pageSize, status, orderNo);
        return Result.success(page);
    }

    @PutMapping("/pay/{id}")
    public Result<?> pay(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        orderService.pay(id, userId);
        return Result.success();
    }

    @PutMapping("/deliver/{id}")
    public Result<?> deliver(@PathVariable Long id, HttpServletRequest request) {
        Long operatorId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        orderService.deliver(id, operatorId, role);
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    public Result<?> complete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        orderService.complete(id, userId);
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    public Result<?> cancel(@PathVariable Long id, HttpServletRequest request) {
        Long operatorId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        orderService.cancel(id, operatorId, role);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
