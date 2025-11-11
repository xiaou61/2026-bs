package com.xiaou.resource.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.resource.common.Result;
import com.xiaou.resource.entity.Question;
import com.xiaou.resource.entity.WrongQuestion;
import com.xiaou.resource.service.QuestionService;
import com.xiaou.resource.service.WrongQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private WrongQuestionService wrongQuestionService;

    @PostMapping("/add")
    public Result<String> addQuestion(@RequestAttribute Long userId, @RequestBody Question question) {
        boolean success = questionService.addQuestion(question, userId);
        return success ? Result.success("添加成功") : Result.error("添加失败");
    }

    @GetMapping("/list")
    public Result<IPage<Question>> getQuestionList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String type) {
        IPage<Question> pageData = questionService.getQuestionList(page, size, subject, difficulty, type);
        return Result.success(pageData);
    }

    @GetMapping("/{id}")
    public Result<Question> getQuestionDetail(@PathVariable Long id) {
        Question question = questionService.getQuestionDetail(id);
        return Result.success(question);
    }

    @PostMapping("/random")
    public Result<List<Question>> getRandomQuestions(@RequestBody Map<String, Object> params) {
        String subject = (String) params.get("subject");
        String difficulty = (String) params.get("difficulty");
        Integer count = Integer.valueOf(params.get("count").toString());
        List<Question> questions = questionService.getRandomQuestions(subject, difficulty, count);
        return Result.success(questions);
    }

    @PostMapping("/generate-paper")
    public Result<List<Question>> generatePaper(@RequestBody Map<String, Object> params) {
        String subject = (String) params.get("subject");
        Integer count = Integer.valueOf(params.get("count").toString());
        List<Question> questions = questionService.generatePaper(subject, count);
        return Result.success(questions);
    }

    @PostMapping("/wrong/add")
    public Result<String> addWrongQuestion(@RequestAttribute Long userId, @RequestBody Map<String, Object> params) {
        Long questionId = Long.valueOf(params.get("questionId").toString());
        String userAnswer = (String) params.get("userAnswer");
        boolean success = wrongQuestionService.addWrongQuestion(userId, questionId, userAnswer);
        return success ? Result.success("添加成功") : Result.error("添加失败");
    }

    @GetMapping("/wrong/list")
    public Result<IPage<WrongQuestion>> getMyWrongQuestions(
            @RequestAttribute Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        IPage<WrongQuestion> pageData = wrongQuestionService.getMyWrongQuestions(userId, page, size);
        return Result.success(pageData);
    }

    @DeleteMapping("/wrong/{id}")
    public Result<String> removeWrongQuestion(@PathVariable Long id, @RequestAttribute Long userId) {
        boolean success = wrongQuestionService.removeWrongQuestion(id, userId);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }
}

