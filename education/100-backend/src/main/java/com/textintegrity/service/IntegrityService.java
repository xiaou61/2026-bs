package com.textintegrity.service;

import com.textintegrity.common.BusinessException;
import com.textintegrity.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Service
public class IntegrityService {

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private CrudService crudService;

    public void runTask(Long taskId) {
        Map<String, Object> task = commonMapper.findTaskById(taskId);
        if (task == null) {
            throw new BusinessException(400, "检测任务不存在");
        }
        Map<String, Object> submission = commonMapper.findSubmissionById(number(task.get("submissionId")));
        if (submission == null) {
            throw new BusinessException(400, "提交文本不存在");
        }
        List<Map<String, Object>> rules = commonMapper.findEnabledRules();
        String text = value(submission.get("title")) + " " + value(submission.get("content")) + " " + value(submission.get("citationNote"));
        StringJoiner matchedRules = new StringJoiner(",");
        int matched = 0;
        int weighted = 0;
        for (Map<String, Object> rule : rules) {
            if (match(text, value(rule.get("keywords")))) {
                matched++;
                weighted += decimal(rule.get("weight")).multiply(new BigDecimal("10")).intValue();
                matchedRules.add(value(rule.get("ruleName")));
            }
        }
        BigDecimal aiProbability = limit(new BigDecimal(28 + matched * 12 + weighted / 3 + signal(text) * 6));
        BigDecimal repeatRate = limit(new BigDecimal(repeat(text) * 9 + matched * 5));
        BigDecimal citationRisk = value(submission.get("citationNote")).isBlank() ? new BigDecimal("82") : limit(new BigDecimal(matched * 8 + 18));
        BigDecimal score = new BigDecimal("100")
                .subtract(aiProbability.multiply(new BigDecimal("0.42")))
                .subtract(repeatRate.multiply(new BigDecimal("0.28")))
                .subtract(citationRisk.multiply(new BigDecimal("0.18")))
                .max(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);
        String riskLevel = score.compareTo(new BigDecimal("60")) < 0 || aiProbability.compareTo(new BigDecimal("75")) >= 0 ? "高" : score.compareTo(new BigDecimal("80")) < 0 ? "中" : "低";
        Map<String, Object> result = new HashMap<>();
        result.put("taskId", taskId);
        result.put("submissionId", submission.get("id"));
        result.put("matchedRules", matchedRules.length() == 0 ? "未命中高风险规则" : matchedRules.toString());
        result.put("aiProbability", aiProbability);
        result.put("repeatRate", repeatRate);
        result.put("citationRisk", citationRisk);
        result.put("riskLevel", riskLevel);
        result.put("score", score);
        result.put("conclusion", buildConclusion(riskLevel, aiProbability, repeatRate, citationRisk));
        result.put("suggestion", buildSuggestion(riskLevel));
        result.put("reviewStatus", 0);
        result.put("createTime", LocalDateTime.now());
        result.put("updateTime", LocalDateTime.now());
        commonMapper.insert("detection_result", result);
        crudService.updateField("task", taskId, "status", 1);
        crudService.updateField("submission", number(submission.get("id")), "status", "高".equals(riskLevel) ? 3 : 2);
        if ("高".equals(riskLevel)) {
            createWarning(number(submission.get("studentId")), result, number(submission.get("id")));
        }
    }

    public void finishTask(Long id) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("status", 2);
        data.put("finishTime", LocalDateTime.now());
        commonMapper.update("detection_task", data);
    }

    public void rejectTask(Long id) {
        crudService.updateField("task", id, "status", 3);
    }

    public void reviewResult(Map<String, Object> data) {
        Long id = number(data.get("id"));
        if (id == null) {
            throw new BusinessException(400, "检测结果ID不能为空");
        }
        data.put("reviewStatus", data.get("reviewStatus") == null ? 1 : data.get("reviewStatus"));
        data.put("updateTime", LocalDateTime.now());
        commonMapper.update("detection_result", data);
    }

    public void handleWarning(Long userId, Map<String, Object> data) {
        Long id = number(data.get("id"));
        if (id == null) {
            throw new BusinessException(400, "预警ID不能为空");
        }
        data.put("handlerId", userId);
        data.put("status", data.get("status") == null ? 1 : data.get("status"));
        data.put("updateTime", LocalDateTime.now());
        commonMapper.update("integrity_warning", data);
    }

    public void reviewRectification(Map<String, Object> data) {
        Long id = number(data.get("id"));
        if (id == null) {
            throw new BusinessException(400, "整改ID不能为空");
        }
        data.put("status", data.get("status") == null ? 1 : data.get("status"));
        data.put("updateTime", LocalDateTime.now());
        commonMapper.update("rectification_record", data);
    }

    public void handleAppeal(Map<String, Object> data) {
        Long id = number(data.get("id"));
        if (id == null) {
            throw new BusinessException(400, "申诉ID不能为空");
        }
        data.put("status", data.get("status") == null ? 1 : data.get("status"));
        data.put("updateTime", LocalDateTime.now());
        commonMapper.update("appeal_record", data);
    }

    public Map<String, Object> dashboard() {
        Map<String, Object> result = new HashMap<>();
        result.put("courseCount", crudService.count("course"));
        result.put("assignmentCount", crudService.count("assignment"));
        result.put("submissionCount", crudService.count("submission"));
        result.put("taskCount", crudService.count("task"));
        result.put("resultCount", crudService.count("result"));
        result.put("highRiskCount", crudService.countWhere("result", "riskLevel", "高"));
        result.put("pendingWarning", crudService.countWhere("warning", "status", 0));
        result.put("pendingAppeal", crudService.countWhere("appeal", "status", 0));
        result.put("pendingRectification", crudService.countWhere("rectification", "status", 0));
        result.put("averageScore", commonMapper.averageScore());
        return result;
    }

    private void createWarning(Long studentId, Map<String, Object> result, Long submissionId) {
        Map<String, Object> warning = new HashMap<>();
        warning.put("resultId", result.get("id"));
        warning.put("studentId", studentId);
        warning.put("warningLevel", "高");
        warning.put("warningTitle", "高风险AI生成文本预警");
        warning.put("warningContent", "提交ID " + submissionId + " 的综合风险较高，请要求学生补充引用说明并提交整改材料");
        warning.put("status", 0);
        warning.put("createTime", LocalDateTime.now());
        warning.put("updateTime", LocalDateTime.now());
        commonMapper.insert("integrity_warning", warning);
    }

    private boolean match(String text, String keywords) {
        if (keywords == null || keywords.isBlank()) {
            return false;
        }
        for (String keyword : keywords.split(",")) {
            if (!keyword.isBlank() && text.contains(keyword.trim())) {
                return true;
            }
        }
        return false;
    }

    private int signal(String text) {
        int score = 0;
        String[] words = {"综上所述", "不可否认", "本文旨在", "显著提升", "多维度", "系统性", "首先", "其次", "最后"};
        for (String word : words) {
            if (text.contains(word)) {
                score++;
            }
        }
        return score;
    }

    private int repeat(String text) {
        int score = 0;
        String[] words = {"重要意义", "研究意义", "实践价值", "优化路径", "发展趋势"};
        for (String word : words) {
            int first = text.indexOf(word);
            if (first >= 0 && text.indexOf(word, first + word.length()) >= 0) {
                score++;
            }
        }
        return score;
    }

    private String buildConclusion(String riskLevel, BigDecimal aiProbability, BigDecimal repeatRate, BigDecimal citationRisk) {
        return "综合风险" + riskLevel + "，AI概率" + aiProbability + "%，重复率" + repeatRate + "%，引用风险" + citationRisk + "%";
    }

    private String buildSuggestion(String riskLevel) {
        if ("高".equals(riskLevel)) {
            return "建议进入人工复核，要求学生补充引用来源、过程材料和修订说明";
        }
        if ("中".equals(riskLevel)) {
            return "建议教师重点查看引用说明和段落风格变化";
        }
        return "建议归档保存，保留抽检记录";
    }

    private BigDecimal limit(BigDecimal value) {
        if (value.compareTo(new BigDecimal("99")) > 0) {
            return new BigDecimal("99.00");
        }
        return value.max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal decimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(String.valueOf(value));
    }

    private Long number(Object value) {
        if (value == null || "".equals(String.valueOf(value))) {
            return null;
        }
        return Long.valueOf(String.valueOf(value));
    }

    private String value(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
}
