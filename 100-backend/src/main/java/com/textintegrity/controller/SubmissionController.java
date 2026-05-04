package com.textintegrity.controller;

import com.github.pagehelper.PageInfo;
import com.textintegrity.common.Result;
import com.textintegrity.service.AccessLogService;
import com.textintegrity.service.AuthService;
import com.textintegrity.service.CrudService;
import com.textintegrity.utils.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {

    @Autowired
    private CrudService crudService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AccessLogService accessLogService;

    @GetMapping("/page")
    public Result<PageInfo<Map<String, Object>>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      String keyword,
                                                      Long assignmentId,
                                                      Long studentId,
                                                      Integer status) {
        return Result.success(crudService.page("submission", pageNum, pageSize, keyword, Filters.of("assignmentId", assignmentId, "studentId", studentId, "status", status)));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertStudent(role);
        data.putIfAbsent("studentId", userId);
        data.putIfAbsent("status", 0);
        data.put("wordCount", wordCount(data.get("content")));
        crudService.save("submission", data);
        accessLogService.record(userId, "文本提交", "新增", "新增提交：" + data.get("title"));
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertStudent(role);
        data.put("wordCount", wordCount(data.get("content")));
        crudService.save("submission", data);
        accessLogService.record(userId, "文本提交", "编辑", "编辑提交：" + data.get("title"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertStudent(role);
        crudService.delete("submission", id);
        accessLogService.record(userId, "文本提交", "删除", "删除提交：" + id);
        return Result.success();
    }

    private int wordCount(Object content) {
        return content == null ? 0 : String.valueOf(content).replaceAll("\\s+", "").length();
    }
}
