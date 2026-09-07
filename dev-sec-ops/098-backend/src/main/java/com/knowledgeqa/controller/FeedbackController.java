package com.knowledgeqa.controller;

import com.github.pagehelper.PageInfo;
import com.knowledgeqa.common.Result;
import com.knowledgeqa.service.AccessLogService;
import com.knowledgeqa.service.AuthService;
import com.knowledgeqa.service.CrudService;
import com.knowledgeqa.service.KnowledgeQaService;
import com.knowledgeqa.utils.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private CrudService crudService;

    @Autowired
    private AuthService authService;

    @Autowired
    private KnowledgeQaService knowledgeQaService;

    @Autowired
    private AccessLogService accessLogService;

    @GetMapping("/page")
    public Result<PageInfo<Map<String, Object>>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      String keyword,
                                                      Long recordId,
                                                      Integer status,
                                                      Integer score) {
        return Result.success(crudService.page("feedback", pageNum, pageSize, keyword, Filters.of("recordId", recordId, "status", status, "score", score)));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertStaff(role);
        data.put("userId", userId);
        data.putIfAbsent("status", 0);
        crudService.save("feedback", data);
        accessLogService.record(userId, "问答反馈", "新增", "提交反馈：" + data.get("recordId"));
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertStaff(role);
        crudService.save("feedback", data);
        accessLogService.record(userId, "问答反馈", "编辑", "编辑反馈：" + data.get("id"));
        return Result.success();
    }

    @PutMapping("/reply")
    public Result<Void> reply(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertEditor(role);
        Long id = Long.valueOf(String.valueOf(data.get("id")));
        Integer status = data.get("status") == null ? 1 : Integer.valueOf(String.valueOf(data.get("status")));
        String replyContent = data.get("replyContent") == null ? "" : String.valueOf(data.get("replyContent"));
        knowledgeQaService.replyFeedback(id, status, replyContent);
        accessLogService.record(userId, "问答反馈", "回复", "回复反馈：" + id);
        return Result.success();
    }
}
