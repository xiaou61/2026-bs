package com.servicequality.controller;

import com.servicequality.common.Result;
import com.servicequality.service.CustomerProfileService;
import com.servicequality.service.KnowledgeArticleService;
import com.servicequality.service.WorkOrderService;
import com.servicequality.service.QualityTaskService;
import com.servicequality.service.QualityResultService;
import com.servicequality.service.RecommendRecordService;
import com.servicequality.service.AgentPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired
    private CustomerProfileService customerService;
    @Autowired
    private KnowledgeArticleService articleService;
    @Autowired
    private WorkOrderService orderService;
    @Autowired
    private QualityTaskService taskService;
    @Autowired
    private QualityResultService resultService;
    @Autowired
    private RecommendRecordService recommendService;
    @Autowired
    private AgentPerformanceService performanceService;

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("customerCount", customerService.count());
        data.put("articleCount", articleService.count());
        data.put("orderCount", orderService.count());
        data.put("taskCount", taskService.count());
        data.put("resultCount", resultService.count());
        data.put("recommendCount", recommendService.count());
        data.put("performanceCount", performanceService.count());
        data.put("todoCount", taskService.count() + recommendService.count());
        return Result.success(data);
    }
}
