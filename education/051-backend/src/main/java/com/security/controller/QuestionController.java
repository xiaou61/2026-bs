package com.security.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.security.common.Result;
import com.security.dto.AnswerDTO;
import com.security.service.QuestionService;
import com.security.vo.AnswerResultVO;
import com.security.vo.QuestionVO;
import com.security.vo.RankVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/daily")
    public Result<List<QuestionVO>> getDailyQuestions(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(questionService.getDailyQuestions(userId));
    }

    @GetMapping("/question/category/{categoryId}")
    public Result<List<QuestionVO>> getCategoryQuestions(@PathVariable Long categoryId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(questionService.getCategoryQuestions(categoryId, userId));
    }

    @PostMapping("/answer/submit")
    public Result<AnswerResultVO> submitAnswer(@RequestBody AnswerDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(questionService.submitAnswer(dto, userId));
    }

    @GetMapping("/answer/wrong-list")
    public Result<Page<QuestionVO>> getWrongList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(questionService.getWrongQuestions(userId, page, size));
    }

    @GetMapping("/rank/list")
    public Result<List<RankVO>> getRankList() {
        return Result.success(questionService.getRankList());
    }
}
