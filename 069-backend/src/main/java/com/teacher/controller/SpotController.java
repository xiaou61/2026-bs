package com.teacher.controller;

import com.teacher.common.BusinessException;
import com.teacher.common.Result;
import com.teacher.entity.ScenicSpot;
import com.teacher.service.SpotService;
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
@RequestMapping("/api/spot")
public class SpotController {

    @Resource
    private SpotService spotService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(required = false) String name,
                          @RequestParam(required = false) String city) {
        return Result.success(spotService.list(name, city));
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) String city,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        return Result.success(spotService.page(pageNum, pageSize, name, city, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody ScenicSpot scenicSpot, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        spotService.save(scenicSpot);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody ScenicSpot scenicSpot, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        spotService.save(scenicSpot);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        spotService.updateStatus(((Number) params.get("id")).longValue(), ((Number) params.get("status")).intValue());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        spotService.deleteById(id);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}


