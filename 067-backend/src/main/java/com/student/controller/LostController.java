package com.student.controller;

import com.student.common.BusinessException;
import com.student.common.Result;
import com.student.entity.LostFound;
import com.student.service.LostFoundService;
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
@RequestMapping("/api/lost")
public class LostController {

    @Resource
    private LostFoundService lostFoundService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String keyword,
                          @RequestParam(required = false) Integer type,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) Long publisherId,
                          HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        Long userId = (Long) request.getAttribute("userId");
        Long finalPublisherId = publisherId;
        if ("STUDENT".equals(role) && Boolean.TRUE.equals(Boolean.valueOf(String.valueOf(request.getParameter("myOnly"))))) {
            finalPublisherId = userId;
        }
        return Result.success(lostFoundService.page(pageNum, pageSize, keyword, type, status, finalPublisherId));
    }

    @PostMapping
    public Result<?> add(@RequestBody LostFound lostFound, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        lostFoundService.save(lostFound, role, userId);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody LostFound lostFound, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        lostFoundService.save(lostFound, role, userId);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        lostFoundService.updateStatus(((Number) params.get("id")).longValue(), ((Number) params.get("status")).intValue(), role, userId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        lostFoundService.deleteById(id, role, userId);
        return Result.success();
    }
}
