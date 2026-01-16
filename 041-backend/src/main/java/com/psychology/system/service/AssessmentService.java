package com.psychology.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.psychology.system.entity.AssessmentRecord;
import com.psychology.system.entity.Question;
import com.psychology.system.entity.Scale;
import com.psychology.system.mapper.AssessmentRecordMapper;
import com.psychology.system.mapper.QuestionMapper;
import com.psychology.system.mapper.ScaleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {
    private final ScaleMapper scaleMapper;
    private final QuestionMapper questionMapper;
    private final AssessmentRecordMapper assessmentRecordMapper;

    public List<Scale> getScales() {
        QueryWrapper<Scale> wrapper = new QueryWrapper<>();
        wrapper.eq("status", "ACTIVE");
        return scaleMapper.selectList(wrapper);
    }

    public Scale getScaleById(Long id) {
        return scaleMapper.selectById(id);
    }

    public List<Question> getQuestionsByScaleId(Long scaleId) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("scale_id", scaleId);
        wrapper.orderByAsc("order_num");
        return questionMapper.selectList(wrapper);
    }

    public AssessmentRecord submitAssessment(Long userId, Long scaleId, String answers) {
        AssessmentRecord record = new AssessmentRecord();
        record.setUserId(userId);
        record.setScaleId(scaleId);
        record.setAnswers(answers);
        record.setStatus("COMPLETED");
        record.setStartedAt(LocalDateTime.now());
        record.setCompletedAt(LocalDateTime.now());
        
        int totalScore = calculateScore(answers);
        record.setTotalScore(totalScore);
        record.setResultLevel(getResultLevel(totalScore));
        record.setReport(generateReport(totalScore));
        
        assessmentRecordMapper.insert(record);
        return record;
    }

    public List<AssessmentRecord> getMyAssessments(Long userId) {
        QueryWrapper<AssessmentRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderByDesc("created_at");
        return assessmentRecordMapper.selectList(wrapper);
    }

    private int calculateScore(String answers) {
        return 50;
    }

    private String getResultLevel(int score) {
        if (score < 40) return "正常";
        if (score < 60) return "轻度";
        if (score < 80) return "中度";
        return "重度";
    }

    private String generateReport(int score) {
        return "您的评分为" + score + "分，属于" + getResultLevel(score) + "水平。";
    }
}
