package com.learningpath.service;

import com.learningpath.mapper.TrainingProgramMapper;
import com.learningpath.mapper.CourseCatalogMapper;
import com.learningpath.mapper.LearnerProfileMapper;
import com.learningpath.mapper.LearningPathPlanMapper;
import com.learningpath.mapper.StudyTaskMapper;
import com.learningpath.mapper.CourseEnrollmentMapper;
import com.learningpath.mapper.AssessmentExamMapper;
import com.learningpath.mapper.ExamScoreMapper;
import com.learningpath.mapper.SkillTagMapper;
import com.learningpath.mapper.CompetencyProfileMapper;
import com.learningpath.mapper.CertificationRecordMapper;
import com.learningpath.mapper.LearningReminderMapper;
import com.learningpath.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final TrainingProgramMapper consumableCatalogMapper;
    private final CourseCatalogMapper supplierProfileMapper;
    private final LearnerProfileMapper labRoomMapper;
    private final LearningPathPlanMapper stockItemMapper;
    private final StudyTaskMapper purchaseRequestMapper;
    private final CourseEnrollmentMapper purchaseApprovalMapper;
    private final AssessmentExamMapper purchaseOrderMapper;
    private final ExamScoreMapper inboundRecordMapper;
    private final SkillTagMapper outboundRecordMapper;
    private final CompetencyProfileMapper inventoryCheckMapper;
    private final CertificationRecordMapper warningRuleMapper;
    private final LearningReminderMapper stockWarningMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("programCount", consumableCatalogMapper.selectCount(null));
        data.put("pathCount", stockItemMapper.selectCount(null));
        data.put("examCount", purchaseRequestMapper.selectCount(null));
        data.put("certCount", stockWarningMapper.selectCount(null));
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






