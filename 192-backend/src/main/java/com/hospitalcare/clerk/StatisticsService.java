package com.hospitalcare.clerk;

import com.hospitalcare.mapper.HospitalWardMapper;
import com.hospitalcare.mapper.PatientProfileMapper;
import com.hospitalcare.mapper.CaregiverProfileMapper;
import com.hospitalcare.mapper.CareAppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final HospitalWardMapper hospitalWardMapper;
    private final PatientProfileMapper patientProfileMapper;
    private final CaregiverProfileMapper caregiverProfileMapper;
    private final CareAppointmentMapper careAppointmentMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("wardCount", hospitalWardMapper.countAll());
        data.put("patientCount", patientProfileMapper.countAll());
        data.put("caregiverCount", caregiverProfileMapper.countAll());
        data.put("appointmentCount", careAppointmentMapper.countAll());
        data.put("trend", Arrays.asList(42, 57, 69, 84, 96, 111, 128));
        data.put("pie", Arrays.asList(map("待审核", 25), map("已派单", 39), map("服务中", 46), map("待评价", 18), map("已结算", 52)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
