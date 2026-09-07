package com.rental.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rental.common.Result;
import com.rental.entity.Contract;
import com.rental.service.ContractService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 租赁合同控制器
 */
@RestController
@RequestMapping("/api/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    /**
     * 创建合同（房东）
     */
    @PostMapping
    public Result<?> create(HttpServletRequest request, @RequestBody Contract contract) {
        Long userId = (Long) request.getAttribute("userId");
        contractService.create(userId, contract);
        return Result.success("合同创建成功");
    }

    /**
     * 获取合同列表
     */
    @GetMapping("/list")
    public Result<IPage<Map<String, Object>>> getList(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer status) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        IPage<Map<String, Object>> result = contractService.getList(userId, role, page, size, status);
        return Result.success(result);
    }

    /**
     * 获取合同详情
     */
    @GetMapping("/{id}")
    public Result<Map<String, Object>> getDetail(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        Map<String, Object> result = contractService.getDetail(userId, id);
        return Result.success(result);
    }

    /**
     * 签署合同（租客）
     */
    @PutMapping("/{id}/sign")
    public Result<?> sign(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        contractService.sign(userId, id);
        return Result.success("签署成功");
    }

    /**
     * 终止合同
     */
    @PutMapping("/{id}/terminate")
    public Result<?> terminate(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        contractService.terminate(userId, id);
        return Result.success("终止成功");
    }
}
