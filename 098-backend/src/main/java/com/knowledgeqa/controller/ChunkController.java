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
@RequestMapping("/api/chunk")
public class ChunkController {

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
                                                      Long documentId,
                                                      Integer vectorStatus) {
        return Result.success(crudService.page("chunk", pageNum, pageSize, keyword, Filters.of("documentId", documentId, "vectorStatus", vectorStatus)));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertEditor(role);
        data.putIfAbsent("vectorStatus", 0);
        crudService.save("chunk", data);
        accessLogService.record(userId, "文档分段", "新增", "新增分段：" + data.get("chunkTitle"));
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> data) {
        authService.assertEditor(role);
        crudService.save("chunk", data);
        accessLogService.record(userId, "文档分段", "编辑", "编辑分段：" + data.get("chunkTitle"));
        return Result.success();
    }

    @PutMapping("/index/{id}")
    public Result<Void> index(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertEditor(role);
        knowledgeQaService.indexChunk(id);
        accessLogService.record(userId, "文档分段", "索引", "生成索引：" + id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute Long userId, @RequestAttribute String role, @PathVariable Long id) {
        authService.assertEditor(role);
        crudService.delete("chunk", id);
        accessLogService.record(userId, "文档分段", "删除", "删除分段：" + id);
        return Result.success();
    }
}
