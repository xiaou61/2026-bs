package com.promptops.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.promptops.common.Result;
import com.promptops.entity.PromptFeedback;
import com.promptops.service.AuthService;
import com.promptops.service.OperationLogService;
import com.promptops.service.PromptFeedbackService;
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
    private PromptFeedbackService promptFeedbackService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/page")
    public Result<Page<PromptFeedback>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             Long assetId,
                                             Integer status,
                                             String priority) {
        return Result.success(promptFeedbackService.page(pageNum, pageSize, assetId, status, priority));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute Long userId, @RequestBody PromptFeedback entity) {
        promptFeedbackService.saveEntity(entity, userId);
        operationLogService.record(userId, "用户反馈", "新增", "提交反馈：" + entity.getAssetId());
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute Long userId, @RequestBody PromptFeedback entity) {
        promptFeedbackService.saveEntity(entity, userId);
        operationLogService.record(userId, "用户反馈", "编辑", "编辑反馈：" + entity.getId());
        return Result.success();
    }

    @PutMapping("/reply")
    public Result<Void> reply(@RequestAttribute Long userId, @RequestAttribute String role, @RequestBody Map<String, Object> body) {
        authService.assertReviewer(role);
        Long id = Long.valueOf(String.valueOf(body.get("id")));
        Integer status = body.get("status") == null ? 1 : Integer.valueOf(String.valueOf(body.get("status")));
        String replyContent = body.get("replyContent") == null ? "" : String.valueOf(body.get("replyContent"));
        promptFeedbackService.reply(id, status, replyContent);
        operationLogService.record(userId, "用户反馈", "回复", "回复反馈：" + id);
        return Result.success();
    }
}
