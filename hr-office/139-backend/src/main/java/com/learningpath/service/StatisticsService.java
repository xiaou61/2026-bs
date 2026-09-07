package com.learningpath.service;

import com.learningpath.mapper.TrainingProgramMapper;
import com.learningpath.mapper.LearningPathPlanMapper;
import com.learningpath.mapper.AssessmentExamMapper;
import com.learningpath.mapper.CertificationRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final TrainingProgramMapper trainingProgramMapper;
    private final LearningPathPlanMapper learningPathPlanMapper;
    private final AssessmentExamMapper assessmentExamMapper;
    private final CertificationRecordMapper certificationRecordMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("programCount", trainingProgramMapper.selectCount(null));
        data.put("pathCount", learningPathPlanMapper.selectCount(null));
        data.put("examCount", assessmentExamMapper.selectCount(null));
        data.put("certCount", certificationRecordMapper.selectCount(null));
        data.put("taskTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("abilityPie", Arrays.asList(map("初级", 35), map("中级", 31), map("高级", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}






