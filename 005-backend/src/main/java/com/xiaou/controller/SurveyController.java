package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Survey;
import com.xiaou.service.SurveyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @PostMapping("/create")
    public Result<String> createSurvey(@RequestBody Survey survey, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        survey.setCreatorId(userId);
        
        Integer userRole = (Integer) request.getAttribute("userRole");
        surveyService.createSurvey(survey, userRole);
        return Result.success("创建成功");
    }

    @GetMapping("/list")
    public Result<List<Survey>> getMySurveys(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<Survey> surveys = surveyService.getMySurveys(userId);
        return Result.success(surveys);
    }

    @PutMapping("/update")
    public Result<String> updateSurvey(@RequestBody Survey survey, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        surveyService.updateSurvey(survey, userId);
        return Result.success("修改成功");
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> deleteSurvey(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        surveyService.deleteSurvey(id, userId);
        return Result.success("删除成功");
    }

    @PutMapping("/publish/{id}")
    public Result<String> publishSurvey(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Integer userRole = (Integer) request.getAttribute("userRole");
        surveyService.publishSurvey(id, userId, userRole);
        return Result.success("发布成功");
    }

    @GetMapping("/detail/{id}")
    public Result<Survey> getSurveyDetail(@PathVariable Long id) {
        Survey survey = surveyService.getById(id);
        return Result.success(survey);
    }
}

