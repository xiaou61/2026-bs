package com.classic.controller;

import com.classic.common.BusinessException;
import com.classic.common.Result;
import com.classic.entity.FormulaInfo;
import com.classic.service.FormulaInfoService;
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
@RequestMapping("/api/formula")
public class FormulaInfoController {

    @Resource
    private FormulaInfoService formulaInfoService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(formulaInfoService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Integer status) {
        return Result.success(formulaInfoService.page(pageNum, pageSize, name, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody FormulaInfo formulaInfo, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        formulaInfoService.save(formulaInfo);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody FormulaInfo formulaInfo, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        formulaInfoService.save(formulaInfo);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        formulaInfoService.deleteById(id);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
