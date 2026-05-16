package com.chroniccare.clerk;

import com.chroniccare.mapper.CommunityClinicMapper;
import com.chroniccare.mapper.ChronicPatientMapper;
import com.chroniccare.mapper.DoctorTeamMapper;
import com.chroniccare.mapper.DiseaseArchiveMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CommunityClinicMapper communityClinicMapper;
    private final ChronicPatientMapper chronicPatientMapper;
    private final DoctorTeamMapper doctorTeamMapper;
    private final DiseaseArchiveMapper diseaseArchiveMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("clinicCount", communityClinicMapper.countAll());
        data.put("patientCount", chronicPatientMapper.countAll());
        data.put("teamCount", doctorTeamMapper.countAll());
        data.put("diseaseCount", diseaseArchiveMapper.countAll());
        data.put("trend", Arrays.asList(58, 74, 88, 105, 121, 137, 154));
        data.put("pie", Arrays.asList(map("待随访", 31), map("随访中", 44), map("需干预", 19), map("已稳定", 68)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
