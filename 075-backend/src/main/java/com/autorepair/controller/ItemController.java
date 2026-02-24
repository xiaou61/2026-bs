package com.autorepair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.autorepair.common.BusinessException;
import com.autorepair.common.Result;
import com.autorepair.entity.RepairService;
import com.autorepair.service.RepairServiceService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Resource
    private RepairServiceService repairServiceService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String title,
                          @RequestParam(required = false) Long categoryId,
                          @RequestParam(required = false) String serviceName) {
        Page<RepairService> page = repairServiceService.list(pageNum, pageSize, title, categoryId, serviceName);
        return Result.success(page);
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String title,
                          @RequestParam(required = false) Long categoryId,
                          @RequestParam(required = false) String serviceName,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        Long userId = (Long) request.getAttribute("userId");
        Page<RepairService> page;
        if ("ADMIN".equals(role)) {
            page = repairServiceService.page(pageNum, pageSize, title, categoryId, serviceName, status, null);
        } else {
            page = repairServiceService.page(pageNum, pageSize, title, categoryId, serviceName, status, userId);
        }
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return Result.success(repairServiceService.getById(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody RepairService item, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        repairServiceService.save(item, userId, role);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody RepairService item, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        repairServiceService.save(item, userId, role);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        Long id = ((Number) params.get("id")).longValue();
        Integer status = ((Number) params.get("status")).intValue();
        repairServiceService.updateStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        repairServiceService.deleteById(id, userId, role);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}



