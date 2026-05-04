package com.knowledgeqa.controller;

import com.github.pagehelper.PageInfo;
import com.knowledgeqa.common.Result;
import com.knowledgeqa.service.AccessLogService;
import com.knowledgeqa.service.AuthService;
import com.knowledgeqa.service.CrudService;
import com.knowledgeqa.utils.Filters;
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
@RequestMapping("/api/space")
public class SpaceController {

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
                                                      Integer status) {
        return Result.success(crudService.page("space", pageNum, pageSize, keyword, Filters.of("status", status)));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertAdmin(role);
        crudService.save("space", data);
        accessLogService.record(userId, "知识空间", "新增", "新增空间：" + data.get("name"));
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertAdmin(role);
        crudService.save("space", data);
        accessLogService.record(userId, "知识空间", "编辑", "编辑空间：" + data.get("name"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        crudService.delete("space", id);
        accessLogService.record(userId, "知识空间", "删除", "删除空间：" + id);
        return Result.success();
    }
}
