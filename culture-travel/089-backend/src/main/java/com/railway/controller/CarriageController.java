package com.railway.controller;

import com.railway.common.Result;
import com.railway.entity.Carriage;
import com.railway.service.CarriageService;
import com.railway.utils.AuthUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/carriage")
public class CarriageController {

    @Resource
    private CarriageService carriageService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String templateName,
                          @RequestParam(required = false) String seatType,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireStaff((String) request.getAttribute("role"));
        return Result.success(carriageService.page(pageNum, pageSize, templateName, seatType, status));
    }

    @GetMapping("/enabled")
    public Result<?> enabled() {
        return Result.success(carriageService.enabledList());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Carriage carriage, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        carriageService.save(carriage);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        carriageService.deleteById(id);
        return Result.success();
    }
}
