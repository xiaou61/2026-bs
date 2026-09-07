package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Question;
import com.xiaou.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/add")
    public Result<String> addQuestion(@RequestBody Question question) {
        questionService.addQuestion(question);
        return Result.success("添加成功");
    }

    @GetMapping("/list/{surveyId}")
    public Result<List<Question>> getQuestions(@PathVariable Long surveyId) {
        List<Question> questions = questionService.getQuestionsBySurveyId(surveyId);
        return Result.success(questions);
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return Result.success("删除成功");
    }
}

