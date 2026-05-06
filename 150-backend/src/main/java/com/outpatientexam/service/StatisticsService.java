package com.outpatientexam.service;

import com.outpatientexam.mapper.ExamItemMapper;
import com.outpatientexam.mapper.PatientProfileMapper;
import com.outpatientexam.mapper.DoctorProfileMapper;
import com.outpatientexam.mapper.ExamAppointmentMapper;
import com.outpatientexam.mapper.ExamDepartmentMapper;
import com.outpatientexam.mapper.CheckinRecordMapper;
import com.outpatientexam.mapper.ExamReportMapper;
import com.outpatientexam.mapper.AbnormalAlertMapper;
import com.outpatientexam.mapper.ReportDeliveryMapper;
import com.outpatientexam.mapper.RevisitAdviceMapper;
import com.outpatientexam.mapper.QueueCallMapper;
import com.outpatientexam.mapper.HospitalNoticeMapper;
import com.outpatientexam.mapper.OperationLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ExamItemMapper examItemMapper;
    private final PatientProfileMapper patientProfileMapper;
    private final DoctorProfileMapper doctorProfileMapper;
    private final ExamAppointmentMapper examAppointmentMapper;
    private final ExamDepartmentMapper examDepartmentMapper;
    private final CheckinRecordMapper checkinRecordMapper;
    private final ExamReportMapper examReportMapper;
    private final AbnormalAlertMapper abnormalAlertMapper;
    private final ReportDeliveryMapper reportDeliveryMapper;
    private final RevisitAdviceMapper revisitAdviceMapper;
    private final QueueCallMapper queueCallMapper;
    private final HospitalNoticeMapper hospitalNoticeMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("itemCount", examItemMapper.countAll());
        data.put("departmentCount", examDepartmentMapper.countAll());
        data.put("reportCount", examReportMapper.countAll());
        data.put("queueCount", queueCallMapper.countAll());
        data.put("examTrend", Arrays.asList(12, 22, 31, 44, 53, 62, 70));
        data.put("examPie", Arrays.asList(map("待检查", 35), map("检查中", 31), map("已完成", 34)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}











