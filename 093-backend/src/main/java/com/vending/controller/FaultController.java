package com.vending.controller;

import com.vending.common.Result;
import com.vending.entity.FaultReport;
import com.vending.service.FaultService;
import com.vending.utils.AuthUtils;
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
@RequestMapping("/api/fault")
public class FaultController {

    @Resource
    private FaultService faultService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String handleStatus,
                          HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");
        return Result.success(faultService.page(pageNum, pageSize, handleStatus, userId, role));
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody FaultReport fault, HttpServletRequest request) {
        faultService.add((Long) request.getAttribute("userId"), fault);
        return Result.success();
    }

    @PutMapping("/handle/{id}")
    public Result<?> handle(@PathVariable Long id, @RequestBody Map<String, String> params, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        faultService.handle(id, (Long) request.getAttribute("userId"), params.get("handleStatus"), params.get("handleResult"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        faultService.deleteById(id);
        return Result.success();
    }
}
