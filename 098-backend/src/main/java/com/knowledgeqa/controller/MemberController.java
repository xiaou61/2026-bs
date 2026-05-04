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
@RequestMapping("/api/member")
public class MemberController {

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
                                                      Long groupId,
                                                      Integer status) {
        return Result.success(crudService.page("member", pageNum, pageSize, keyword, Filters.of("groupId", groupId, "status", status)));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertAdmin(role);
        crudService.save("member", data);
        accessLogService.record(userId, "组成员", "新增", "新增组成员：" + data.get("userId"));
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertAdmin(role);
        crudService.save("member", data);
        accessLogService.record(userId, "组成员", "编辑", "编辑组成员：" + data.get("userId"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        crudService.delete("member", id);
        accessLogService.record(userId, "组成员", "删除", "删除组成员：" + id);
        return Result.success();
    }
}
