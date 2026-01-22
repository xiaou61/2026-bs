package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.dto.AnswerDTO;
import com.xiaou.service.QuestionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int current,
                         @RequestParam(defaultValue = "10") int size,
                         @RequestParam(required = false) Long subjectId,
                         @RequestParam(required = false) Long categoryId,
                         @RequestParam(required = false) Integer type,
                         @RequestParam(required = false) Integer difficulty) {
        return Result.success(questionService.getPage(current, size, subjectId, categoryId, type, difficulty));
    }

    @GetMapping("/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return Result.success(questionService.getDetail(id));
    }

    @GetMapping("/random")
    public Result<?> random(@RequestParam(required = false) Long subjectId,
                           @RequestParam(defaultValue = "10") Integer count) {
        return Result.success(questionService.getRandom(subjectId, count));
    }

    @PostMapping("/submit")
    public Result<?> submitAnswer(@RequestBody AnswerDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(questionService.submitAnswer(userId, dto));
    }

    @GetMapping("/wrong")
    public Result<?> wrongQuestions(@RequestParam(defaultValue = "1") int current,
                                   @RequestParam(defaultValue = "10") int size,
                                   HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(questionService.getWrongQuestions(userId, current, size));
    }

    @PostMapping("/wrong/{questionId}/mastered")
    public Result<?> markMastered(@PathVariable Long questionId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        questionService.markMastered(userId, questionId);
        return Result.success();
    }
}
