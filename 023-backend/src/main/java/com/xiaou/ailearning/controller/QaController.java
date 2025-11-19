package com.xiaou.ailearning.controller;

import com.xiaou.ailearning.common.Result;
import com.xiaou.ailearning.entity.QaRecord;
import com.xiaou.ailearning.service.QaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/qa")
public class QaController {

    @Autowired
    private QaService qaService;

    @PostMapping("/ask")
    public Result<Map<String, Object>> askQuestion(@RequestAttribute Long userId, @RequestBody Map<String, String> request) {
        try {
            String question = request.get("question");
            String sessionId = request.getOrDefault("sessionId", UUID.randomUUID().toString());
            
            if (question == null || question.trim().isEmpty()) {
                return Result.error("问题不能为空");
            }
            
            String answer = qaService.answerQuestion(userId, question, sessionId);
            
            Map<String, Object> response = Map.of(
                "answer", answer,
                "sessionId", sessionId,
                "question", question
            );
            
            return Result.success(response);
        } catch (Exception e) {
            return Result.error("回答问题失败: " + e.getMessage());
        }
    }

    @GetMapping("/history")
    public Result<List<QaRecord>> getQaHistory(@RequestAttribute Long userId, @RequestParam(defaultValue = "20") int limit) {
        try {
            List<QaRecord> history = qaService.getQaHistory(userId, limit);
            return Result.success(history);
        } catch (Exception e) {
            return Result.error("获取问答历史失败: " + e.getMessage());
        }
    }

    @GetMapping("/session/{sessionId}")
    public Result<List<QaRecord>> getSessionHistory(@PathVariable String sessionId) {
        try {
            List<QaRecord> sessionHistory = qaService.getSessionHistory(sessionId);
            return Result.success(sessionHistory);
        } catch (Exception e) {
            return Result.error("获取会话历史失败: " + e.getMessage());
        }
    }

    @PostMapping("/feedback/{qaRecordId}")
    public Result<Void> submitFeedback(@PathVariable Long qaRecordId, @RequestBody Map<String, Boolean> request) {
        try {
            Boolean isHelpful = request.get("isHelpful");
            qaService.saveFeedback(qaRecordId, isHelpful);
            return Result.success("反馈提交成功");
        } catch (Exception e) {
            return Result.error("反馈提交失败: " + e.getMessage());
        }
    }

    @PostMapping("/analyze")
    public Result<Map<String, Object>> analyzeQuestion(@RequestBody Map<String, String> request) {
        try {
            String question = request.get("question");
            if (question == null || question.trim().isEmpty()) {
                return Result.error("问题不能为空");
            }
            
            Map<String, Object> analysis = qaService.analyzeQuestion(question);
            return Result.success(analysis);
        } catch (Exception e) {
            return Result.error("问题分析失败: " + e.getMessage());
        }
    }

    @GetMapping("/keywords")
    public Result<List<String>> extractKeywords(@RequestParam String text) {
        try {
            List<String> keywords = qaService.extractKeywords(text);
            return Result.success(keywords);
        } catch (Exception e) {
            return Result.error("关键词提取失败: " + e.getMessage());
        }
    }
}