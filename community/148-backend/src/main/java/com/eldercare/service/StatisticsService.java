package com.eldercare.service;

import com.eldercare.mapper.ServicePackageMapper;
import com.eldercare.mapper.ElderProfileMapper;
import com.eldercare.mapper.CaregiverProfileMapper;
import com.eldercare.mapper.ServiceOrderMapper;
import com.eldercare.mapper.CareTeamMapper;
import com.eldercare.mapper.VisitCheckinMapper;
import com.eldercare.mapper.ServiceRecordMapper;
import com.eldercare.mapper.HealthAssessmentMapper;
import com.eldercare.mapper.MedicationReminderMapper;
import com.eldercare.mapper.FamilyVisitMapper;
import com.eldercare.mapper.AlertEventMapper;
import com.eldercare.mapper.CareNoticeMapper;
import com.eldercare.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ServicePackageMapper servicePackageMapper;
    private final ElderProfileMapper elderProfileMapper;
    private final CaregiverProfileMapper caregiverProfileMapper;
    private final ServiceOrderMapper serviceOrderMapper;
    private final CareTeamMapper careTeamMapper;
    private final VisitCheckinMapper visitCheckinMapper;
    private final ServiceRecordMapper serviceRecordMapper;
    private final HealthAssessmentMapper healthAssessmentMapper;
    private final MedicationReminderMapper medicationReminderMapper;
    private final FamilyVisitMapper familyVisitMapper;
    private final AlertEventMapper alertEventMapper;
    private final CareNoticeMapper careNoticeMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("packageCount", servicePackageMapper.countAll());
        data.put("teamCount", careTeamMapper.countAll());
        data.put("recordCount", serviceRecordMapper.countAll());
        data.put("alertCount", alertEventMapper.countAll());
        data.put("careTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("carePie", Arrays.asList(map("待服务", 35), map("服务中", 31), map("已完成", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}










