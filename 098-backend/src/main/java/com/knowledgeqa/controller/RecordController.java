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
@RequestMapping("/api/record")
public class RecordController {

    @Autowired
    private CrudService crudService;

    @Autowired
    private KnowledgeQaService knowledgeQaService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AccessLogService accessLogService;

    @GetMapping("/page")
    public Result<PageInfo<Map<String, Object>>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      String keyword,
                                                      Long sessionId,
                                                      Integer resolved,
                                                      Integer status) {
        return Result.success(crudService.page("record", pageNum, pageSize, keyword, Filters.of("sessionId", sessionId, "resolved", resolved, "status", status)));
    }

    @PostMapping("/ask")
    public Result<Map<String, Object>> ask(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertStaff(role);
        Long sessionId = Long.valueOf(String.valueOf(data.get("sessionId")));
        String question = String.valueOf(data.get("question"));
        Map<String, Object> record = knowledgeQaService.ask(userId, sessionId, question);
        accessLogService.record(userId, "问答记录", "提问", "提交问题：" + question);
        return Result.success(record);
    }

    @PutMapping("/resolve/{id}/{resolved}")
    public Result<Void> resolve(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id, @PathVariable Integer resolved) {
        authService.assertStaff(role);
        knowledgeQaService.resolve(id, resolved);
        accessLogService.record(userId, "问答记录", "解决状态", "标记问答：" + id);
        return Result.success();
    }
}
