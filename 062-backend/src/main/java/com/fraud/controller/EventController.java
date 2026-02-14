package com.fraud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.common.Result;
import com.fraud.entity.RiskEvent;
import com.fraud.service.OperationLogService;
import com.fraud.service.RiskEventService;
import com.fraud.vo.RiskEventVO;
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
@RequestMapping("/api/event")
public class EventController {

    @Resource
    private RiskEventService riskEventService;

    @Resource
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String eventNo,
                          @RequestParam(required = false) String riskLevel,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        Page<RiskEventVO> page = riskEventService.page(pageNum, pageSize, eventNo, riskLevel, status, null);
        return Result.success(page);
    }

    @GetMapping("/my")
    public Result<?> my(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) String eventNo,
                        @RequestParam(required = false) String riskLevel,
                        @RequestParam(required = false) Integer status,
                        HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Page<RiskEventVO> page = riskEventService.page(pageNum, pageSize, eventNo, riskLevel, status, userId);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(riskEventService.detail(id, userId, role));
    }

    @PostMapping("/report")
    public Result<?> report(@RequestBody RiskEvent event, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        RiskEvent result = riskEventService.report(event, userId);
        operationLogService.add("EVENT", "REPORT", userId, (String) request.getAttribute("role"), result.getEventNo(), "上报风险事件");
        return Result.success(result);
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        Long id = ((Number) params.get("id")).longValue();
        Integer status = ((Number) params.get("status")).intValue();
        riskEventService.updateStatus(id, status);
        operationLogService.add("EVENT", "STATUS", (Long) request.getAttribute("userId"), (String) request.getAttribute("role"), String.valueOf(id), "更新事件状态:" + status);
        return Result.success();
    }

    private void checkRiskRole(String role) {
        if (!"ADMIN".equals(role) && !"ANALYST".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
