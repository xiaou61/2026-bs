package com.fraud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.common.Result;
import com.fraud.entity.FraudCase;
import com.fraud.service.FraudCaseService;
import com.fraud.service.OperationLogService;
import com.fraud.vo.FraudCaseVO;
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

@RestController
@RequestMapping("/api/case")
public class CaseController {

    @Resource
    private FraudCaseService fraudCaseService;

    @Resource
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String caseNo,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) Integer priority,
                          @RequestParam(required = false) Long ownerId,
                          HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        Page<FraudCaseVO> page = fraudCaseService.page(pageNum, pageSize, caseNo, status, priority, ownerId);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id, HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        return Result.success(fraudCaseService.detail(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody FraudCase fraudCase, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        checkRiskRole(role);
        Long userId = (Long) request.getAttribute("userId");
        fraudCaseService.save(fraudCase, userId, role);
        String bizNo = fraudCase.getCaseNo() == null ? String.valueOf(fraudCase.getAlertId()) : fraudCase.getCaseNo();
        operationLogService.add("CASE", "ADD", userId, role, bizNo, "新增案件");
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody FraudCase fraudCase, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        checkRiskRole(role);
        Long userId = (Long) request.getAttribute("userId");
        fraudCaseService.save(fraudCase, userId, role);
        operationLogService.add("CASE", "UPDATE", userId, role, String.valueOf(fraudCase.getId()), "更新案件");
        return Result.success();
    }

    @PutMapping("/close/{id}")
    public Result<?> close(@PathVariable Long id,
                           @RequestParam(required = false) String result,
                           HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        checkRiskRole(role);
        Long userId = (Long) request.getAttribute("userId");
        fraudCaseService.close(id, userId, role, result);
        operationLogService.add("CASE", "CLOSE", userId, role, String.valueOf(id), "关闭案件");
        return Result.success();
    }

    private void checkRiskRole(String role) {
        if (!"ADMIN".equals(role) && !"ANALYST".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
