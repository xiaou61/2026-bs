package com.rental.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rental.common.Result;
import com.rental.entity.Repair;
import com.rental.service.RepairService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/repair")
public class RepairController {

    @Autowired
    private RepairService repairService;

    @PostMapping
    public Result<?> create(HttpServletRequest request, @RequestBody Repair repair) {
        Long userId = (Long) request.getAttribute("userId");
        repairService.create(userId, repair);
        return Result.success("报修提交成功");
    }

    @GetMapping("/list")
    public Result<IPage<Map<String, Object>>> getList(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer status) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(repairService.getList(userId, role, page, size, status));
    }

    @PutMapping("/{id}/process")
    public Result<?> process(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        repairService.process(userId, id);
        return Result.success("开始处理");
    }

    @PutMapping("/{id}/complete")
    public Result<?> complete(HttpServletRequest request, @PathVariable Long id, @RequestBody CompleteDTO dto) {
        Long userId = (Long) request.getAttribute("userId");
        repairService.complete(userId, id, dto.getResult());
        return Result.success("处理完成");
    }

    @Data
    public static class CompleteDTO {
        private String result;
    }
}
