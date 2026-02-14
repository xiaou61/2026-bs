package com.fraud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.common.Result;
import com.fraud.entity.Appeal;
import com.fraud.service.AppealService;
import com.fraud.service.OperationLogService;
import com.fraud.vo.AppealVO;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/appeal")
public class AppealController {

    @Resource
    private AppealService appealService;

    @Resource
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) String alertNo,
                          HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        Page<AppealVO> page = appealService.page(pageNum, pageSize, status, alertNo);
        return Result.success(page);
    }

    @GetMapping("/my")
    public Result<?> my(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) Integer status,
                        @RequestParam(required = false) String alertNo,
                        HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Page<AppealVO> page = appealService.myPage(userId, pageNum, pageSize, status, alertNo);
        return Result.success(page);
    }

    @PostMapping
    public Result<?> add(@RequestBody Appeal appeal, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        appealService.add(appeal, userId);
        operationLogService.add("APPEAL", "ADD", userId, (String) request.getAttribute("role"), String.valueOf(appeal.getAlertId()), "提交申诉");
        return Result.success();
    }

    @PutMapping("/reply")
    public Result<?> reply(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("reply") == null) {
            throw new BusinessException("参数不完整");
        }
        Long id = ((Number) params.get("id")).longValue();
        String reply = params.get("reply").toString();
        Integer alertStatus = params.get("alertStatus") == null ? null : ((Number) params.get("alertStatus")).intValue();
        Long adminId = (Long) request.getAttribute("userId");
        appealService.reply(id, reply, adminId, alertStatus);
        operationLogService.add("APPEAL", "REPLY", adminId, (String) request.getAttribute("role"), String.valueOf(id), "回复申诉");
        return Result.success();
    }

    private void checkRiskRole(String role) {
        if (!"ADMIN".equals(role) && !"ANALYST".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
