package com.knowledgeqa.controller;

import com.knowledgeqa.common.Result;
import com.knowledgeqa.service.KnowledgeQaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private KnowledgeQaService knowledgeQaService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard() {
        return Result.success(knowledgeQaService.dashboard());
    }
}
