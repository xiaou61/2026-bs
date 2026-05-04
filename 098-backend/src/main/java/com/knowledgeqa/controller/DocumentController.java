package com.knowledgeqa.controller;

import com.github.pagehelper.PageInfo;
import com.knowledgeqa.common.Result;
import com.knowledgeqa.service.AccessLogService;
import com.knowledgeqa.service.AuthService;
import com.knowledgeqa.service.CrudService;
import com.knowledgeqa.service.KnowledgeQaService;
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
@RequestMapping("/api/document")
public class DocumentController {

    @Autowired
    private CrudService crudService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AccessLogService accessLogService;

    @Autowired
    private KnowledgeQaService knowledgeQaService;

    @GetMapping("/page")
    public Result<PageInfo<Map<String, Object>>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      String keyword,
                                                      Long spaceId,
                                                      Long categoryId,
                                                      Integer status) {
        return Result.success(crudService.page("document", pageNum, pageSize, keyword, Filters.of("spaceId", spaceId, "categoryId", categoryId, "status", status)));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertEditor(role);
        data.put("creatorId", userId);
        data.putIfAbsent("status", 0);
        crudService.save("document", data);
        accessLogService.record(userId, "知识文档", "新增", "新增文档：" + data.get("title"));
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertEditor(role);
        crudService.save("document", data);
        accessLogService.record(userId, "知识文档", "编辑", "编辑文档：" + data.get("title"));
        return Result.success();
    }

    @PutMapping("/publish/{id}")
    public Result<Void> publish(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertEditor(role);
        knowledgeQaService.publishDocument(id);
        accessLogService.record(userId, "知识文档", "发布", "发布文档：" + id);
        return Result.success();
    }

    @PutMapping("/archive/{id}")
    public Result<Void> archive(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertEditor(role);
        knowledgeQaService.archiveDocument(id);
        accessLogService.record(userId, "知识文档", "归档", "归档文档：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertEditor(role);
        crudService.delete("document", id);
        accessLogService.record(userId, "知识文档", "删除", "删除文档：" + id);
        return Result.success();
    }
}
