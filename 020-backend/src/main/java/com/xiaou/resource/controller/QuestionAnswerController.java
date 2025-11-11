package com.xiaou.resource.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.resource.common.Result;
import com.xiaou.resource.entity.Answer;
import com.xiaou.resource.entity.QuestionAnswer;
import com.xiaou.resource.service.AnswerService;
import com.xiaou.resource.service.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/qa")
public class QuestionAnswerController {

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Autowired
    private AnswerService answerService;

    @PostMapping("/ask")
    public Result<String> askQuestion(@RequestAttribute Long userId, @RequestBody QuestionAnswer questionAnswer) {
        boolean success = questionAnswerService.askQuestion(questionAnswer, userId);
        return success ? Result.success("提问成功") : Result.error("积分不足或提问失败");
    }

    @GetMapping("/list")
    public Result<IPage<QuestionAnswer>> getQuestionList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status) {
        IPage<QuestionAnswer> pageData = questionAnswerService.getQuestionList(page, size, category, status);
        return Result.success(pageData);
    }

    @GetMapping("/{id}")
    public Result<QuestionAnswer> getQuestionDetail(@PathVariable Long id) {
        QuestionAnswer question = questionAnswerService.getQuestionDetail(id);
        return Result.success(question);
    }

    @GetMapping("/my")
    public Result<IPage<QuestionAnswer>> getMyQuestions(
            @RequestAttribute Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        IPage<QuestionAnswer> pageData = questionAnswerService.getMyQuestions(userId, page, size);
        return Result.success(pageData);
    }

    @PostMapping("/answer")
    public Result<String> addAnswer(@RequestAttribute Long userId, @RequestBody Answer answer) {
        boolean success = answerService.addAnswer(answer, userId);
        return success ? Result.success("回答成功") : Result.error("回答失败");
    }

    @GetMapping("/answers/{questionId}")
    public Result<List<Answer>> getAnswers(@PathVariable Long questionId) {
        List<Answer> answers = answerService.getAnswersByQuestionId(questionId);
        return Result.success(answers);
    }

    @PostMapping("/accept")
    public Result<String> acceptAnswer(@RequestAttribute Long userId, @RequestBody Map<String, Long> params) {
        Long answerId = params.get("answerId");
        boolean success = answerService.acceptAnswer(answerId, userId);
        return success ? Result.success("采纳成功") : Result.error("采纳失败");
    }

    @PostMapping("/like/{answerId}")
    public Result<String> likeAnswer(@PathVariable Long answerId) {
        boolean success = answerService.likeAnswer(answerId);
        return success ? Result.success("点赞成功") : Result.error("点赞失败");
    }
}

