package com.fraud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.common.BusinessException;
import com.fraud.common.Result;
import com.fraud.entity.Blacklist;
import com.fraud.service.BlacklistService;
import com.fraud.service.OperationLogService;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/blacklist")
public class BlacklistController {

    @Resource
    private BlacklistService blacklistService;

    @Resource
    private OperationLogService operationLogService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(blacklistService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String type,
                          @RequestParam(required = false) String value,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        Page<Blacklist> page = blacklistService.page(pageNum, pageSize, type, value, status);
        return Result.success(page);
    }

    @PostMapping
    public Result<?> add(@RequestBody Blacklist blacklist, HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        Long userId = (Long) request.getAttribute("userId");
        blacklistService.save(blacklist, userId);
        operationLogService.add("BLACKLIST", "ADD", userId, (String) request.getAttribute("role"), blacklist.getValue(), "新增黑名单");
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Blacklist blacklist, HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        Long userId = (Long) request.getAttribute("userId");
        blacklistService.save(blacklist, userId);
        operationLogService.add("BLACKLIST", "UPDATE", userId, (String) request.getAttribute("role"), String.valueOf(blacklist.getId()), "更新黑名单");
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkRiskRole((String) request.getAttribute("role"));
        blacklistService.deleteById(id);
        operationLogService.add("BLACKLIST", "DELETE", (Long) request.getAttribute("userId"), (String) request.getAttribute("role"), String.valueOf(id), "删除黑名单");
        return Result.success();
    }

    private void checkRiskRole(String role) {
        if (!"ADMIN".equals(role) && !"ANALYST".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
