package com.teacher.new.controller;

import com.teacher.new.common.BusinessException;
import com.teacher.new.common.Result;
import com.teacher.new.entity.SubjectInfo;
import com.teacher.new.service.SubjectService;
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
@RequestMapping("/api/subject")
public class SubjectController {

    @Resource
    private SubjectService subjectService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(subjectService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String subjectName,
                          @RequestParam(required = false) String subjectCode,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        return Result.success(subjectService.page(pageNum, pageSize, subjectName, subjectCode, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody SubjectInfo subject, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        subjectService.save(subject);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody SubjectInfo subject, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        subjectService.save(subject);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("status") == null) {
            throw new BusinessException("参数不完整");
        }
        subjectService.updateStatus(((Number) params.get("id")).longValue(), ((Number) params.get("status")).intValue());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        subjectService.deleteById(id);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
