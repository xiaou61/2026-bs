package com.fraud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.common.Result;
import com.fraud.service.FraudAlertService;
import com.fraud.service.OperationLogService;
import com.fraud.vo.FraudAlertVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/alert")
public class AlertController {

    @Resource
    private FraudAlertService fraudAlertService;

    @Resource
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String alertNo,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) String riskLevel,
                          @RequestParam(required = false) String eventNo,
                          HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        Page<FraudAlertVO> page = fraudAlertService.page(pageNum, pageSize, alertNo, status, riskLevel, eventNo);
        return Result.success(page);
    }

    @GetMapping("/my")
    public Result<?> my(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) Integer status,
                        HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Page<FraudAlertVO> page = fraudAlertService.myPage(userId, pageNum, pageSize, status);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(fraudAlertService.detail(id, userId, role));
    }

    @PutMapping("/assign")
    public Result<?> assign(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("assigneeId") == null) {
            throw new BusinessException("参数不完整");
        }
        Long id = ((Number) params.get("id")).longValue();
        Long assigneeId = ((Number) params.get("assigneeId")).longValue();
        fraudAlertService.assign(id, assigneeId);
        operationLogService.add("ALERT", "ASSIGN", (Long) request.getAttribute("userId"), (String) request.getAttribute("role"), String.valueOf(id), "指派预警处理人:" + assigneeId);
        return Result.success();
    }

    @PutMapping("/handle")
    public Result<?> handle(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        Long id = ((Number) params.get("id")).longValue();
        Integer status = ((Number) params.get("status")).intValue();
        String handleResult = params.get("handleResult") == null ? null : params.get("handleResult").toString();
        String handleRemark = params.get("handleRemark") == null ? null : params.get("handleRemark").toString();
        Long operatorId = (Long) request.getAttribute("userId");
        fraudAlertService.handle(id, status, handleResult, handleRemark, operatorId);
        operationLogService.add("ALERT", "HANDLE", operatorId, (String) request.getAttribute("role"), String.valueOf(id), "处置预警状态:" + status);
        return Result.success();
    }

    private void checkRiskRole(String role) {
        if (!"ADMIN".equals(role) && !"ANALYST".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
