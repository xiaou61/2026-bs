package com.psychologycare.service;

import com.psychologycare.mapper.CounselCaseMapper;
import com.psychologycare.mapper.CounselRoomMapper;
import com.psychologycare.mapper.StudentProfileMapper;
import com.psychologycare.mapper.DutyScheduleMapper;
import com.psychologycare.mapper.AppointmentRequestMapper;
import com.psychologycare.mapper.CounselRecordMapper;
import com.psychologycare.mapper.AssessmentQuestionnaireMapper;
import com.psychologycare.mapper.RiskAssessmentMapper;
import com.psychologycare.mapper.CrisisInterventionMapper;
import com.psychologycare.mapper.FamilyCommunicationMapper;
import com.psychologycare.mapper.FollowUpPlanMapper;
import com.psychologycare.mapper.SystemNoticeMapper;
import com.psychologycare.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CounselCaseMapper counselCaseMapper;
    private final CounselRoomMapper counselRoomMapper;
    private final StudentProfileMapper studentProfileMapper;
    private final DutyScheduleMapper dutyScheduleMapper;
    private final AppointmentRequestMapper appointmentRequestMapper;
    private final CounselRecordMapper counselRecordMapper;
    private final AssessmentQuestionnaireMapper assessmentQuestionnaireMapper;
    private final RiskAssessmentMapper riskAssessmentMapper;
    private final CrisisInterventionMapper crisisInterventionMapper;
    private final FamilyCommunicationMapper familyCommunicationMapper;
    private final FollowUpPlanMapper followUpPlanMapper;
    private final SystemNoticeMapper systemNoticeMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("caseCount", counselCaseMapper.selectCount(null));
        data.put("appointmentCount", appointmentRequestMapper.selectCount(null));
        data.put("questionnaireCount", assessmentQuestionnaireMapper.selectCount(null));
        data.put("noticeCount", systemNoticeMapper.selectCount(null));
        data.put("counselTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("counselPie", Arrays.asList(map("待预约", 35), map("咨询中", 31), map("已结案", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}










