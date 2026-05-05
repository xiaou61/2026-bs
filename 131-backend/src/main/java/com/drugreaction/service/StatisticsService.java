package com.drugreaction.service;

import com.drugreaction.mapper.PatientProfileMapper;
import com.drugreaction.mapper.DrugCatalogMapper;
import com.drugreaction.mapper.ReporterProfileMapper;
import com.drugreaction.mapper.AdverseReportMapper;
import com.drugreaction.mapper.ReactionSymptomMapper;
import com.drugreaction.mapper.RiskAssessmentMapper;
import com.drugreaction.mapper.FollowupPlanMapper;
import com.drugreaction.mapper.FollowupRecordMapper;
import com.drugreaction.mapper.CaseReviewMapper;
import com.drugreaction.mapper.TreatmentAdviceMapper;
import com.drugreaction.mapper.DepartmentInfoMapper;
import com.drugreaction.mapper.StatisticsReportMapper;
import com.drugreaction.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final PatientProfileMapper patientProfileMapper;
    private final DrugCatalogMapper drugCatalogMapper;
    private final ReporterProfileMapper reporterProfileMapper;
    private final AdverseReportMapper adverseReportMapper;
    private final ReactionSymptomMapper reactionSymptomMapper;
    private final RiskAssessmentMapper riskAssessmentMapper;
    private final FollowupPlanMapper followupPlanMapper;
    private final FollowupRecordMapper followupRecordMapper;
    private final CaseReviewMapper caseReviewMapper;
    private final TreatmentAdviceMapper treatmentAdviceMapper;
    private final DepartmentInfoMapper departmentInfoMapper;
    private final StatisticsReportMapper statisticsReportMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("patientCount", patientProfileMapper.selectCount(null));
        data.put("reportCount", adverseReportMapper.selectCount(null));
        data.put("riskCount", riskAssessmentMapper.selectCount(null));
        data.put("followupCount", followupRecordMapper.selectCount(null));
        data.put("reportTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("severityPie", Arrays.asList(map("一般", 42), map("严重", 28), map("待复核", 30)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
