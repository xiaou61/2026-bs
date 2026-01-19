package com.rental.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rental.common.Result;
import com.rental.service.BillService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 租金账单控制器
 */
@RestController
@RequestMapping("/api/bill")
public class BillController {

    @Autowired
    private BillService billService;

    /**
     * 获取账单列表
     */
    @GetMapping("/list")
    public Result<IPage<Map<String, Object>>> getList(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer status) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        IPage<Map<String, Object>> result = billService.getList(userId, role, page, size, status);
        return Result.success(result);
    }

    /**
     * 支付账单
     */
    @PostMapping("/{id}/pay")
    public Result<?> pay(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        billService.pay(userId, id);
        return Result.success("支付成功");
    }

    /**
     * 获取账单统计
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getStatistics(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        Map<String, Object> result = billService.getStatistics(userId, role);
        return Result.success(result);
    }
}
