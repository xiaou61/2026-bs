package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.AnswerDetail;
import com.xiaou.service.AnswerService;
import com.xiaou.service.ExportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private ExportService exportService;

    @PostMapping("/submit")
    public Result<String> submitAnswer(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long surveyId = Long.valueOf(params.get("surveyId").toString());
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> details = (List<Map<String, Object>>) params.get("details");
        
        String userIp = request.getRemoteAddr();
        
        List<AnswerDetail> answerDetails = details.stream().map(detail -> {
            AnswerDetail ad = new AnswerDetail();
            ad.setQuestionId(Long.valueOf(detail.get("questionId").toString()));
            ad.setValue(detail.get("value").toString());
            return ad;
        }).toList();
        
        answerService.submitAnswer(surveyId, userIp, answerDetails);
        return Result.success("提交成功");
    }

    @GetMapping("/stat/{surveyId}")
    public Result<Map<String, Object>> getStatistics(@PathVariable Long surveyId) {
        Map<String, Object> stats = answerService.getStatistics(surveyId);
        return Result.success(stats);
    }

    @GetMapping("/export/{surveyId}")
    public void exportExcel(@PathVariable Long surveyId, HttpServletResponse response) {
        try {
            byte[] data = exportService.exportSurveyData(surveyId);
            
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            String fileName = URLEncoder.encode("问卷统计数据_" + surveyId + ".xlsx", StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            
            response.getOutputStream().write(data);
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

