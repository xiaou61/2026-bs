package com.disaster.controller;

import com.disaster.common.Result;
import com.disaster.entity.Disaster;
import com.disaster.service.DisasterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/disaster")
public class DisasterController {

    @Autowired
    private DisasterService disasterService;

    @PostMapping("/report")
    public Result<Void> report(@RequestBody Disaster disaster, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        disasterService.report(disaster, userId);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "10") int pageSize,
                          @RequestParam(required = false) String type,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) Integer level,
                          @RequestParam(required = false) String keyword) {
        return Result.success(disasterService.page(pageNum, pageSize, type, status, level, keyword));
    }

    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(disasterService.listAll());
    }

    @GetMapping("/{id}")
    public Result<Disaster> detail(@PathVariable Long id) {
        return Result.success(disasterService.getById(id));
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody Disaster disaster) {
        disaster.setId(id);
        disasterService.update(disaster);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        disasterService.updateStatus(id, params.get("status"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        disasterService.delete(id);
        return Result.success();
    }

    @GetMapping("/stats")
    public Result<?> stats() {
        return Result.success(disasterService.stats());
    }
}
