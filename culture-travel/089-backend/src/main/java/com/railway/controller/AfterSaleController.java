package com.railway.controller;

import com.railway.common.Result;
import com.railway.dto.AfterSaleApplyDTO;
import com.railway.service.AfterSaleService;
import com.railway.utils.AuthUtils;
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
@RequestMapping("/api/after-sale")
public class AfterSaleController {

    @Resource
    private AfterSaleService afterSaleService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String serviceType,
                          @RequestParam(required = false) String reviewStatus,
                          HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(afterSaleService.page(pageNum, pageSize, serviceType, reviewStatus, userId, role));
    }

    @PostMapping("/apply")
    public Result<?> apply(@RequestBody AfterSaleApplyDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        afterSaleService.apply(userId, dto);
        return Result.success();
    }

    @PutMapping("/review/{id}")
    public Result<?> review(@PathVariable Long id, @RequestBody Map<String, String> params, HttpServletRequest request) {
        AuthUtils.requireStaff((String) request.getAttribute("role"));
        afterSaleService.review(id, params.get("reviewStatus"), params.get("reviewRemark"));
        return Result.success();
    }
}
