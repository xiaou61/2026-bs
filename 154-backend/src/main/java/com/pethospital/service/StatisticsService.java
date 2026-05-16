package com.pethospital.service;

import com.pethospital.mapper.PetOwnerMapper;
import com.pethospital.mapper.PetProfileMapper;
import com.pethospital.mapper.DoctorProfileMapper;
import com.pethospital.mapper.ClinicScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final PetOwnerMapper petOwnerMapper;
    private final PetProfileMapper petProfileMapper;
    private final DoctorProfileMapper doctorProfileMapper;
    private final ClinicScheduleMapper clinicScheduleMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("ownerCount", petOwnerMapper.countAll());
        data.put("petCount", petProfileMapper.countAll());
        data.put("doctorCount", doctorProfileMapper.countAll());
        data.put("scheduleCount", clinicScheduleMapper.countAll());
        data.put("trend", Arrays.asList(120, 146, 163, 188, 201, 234, 260));
        data.put("pie", Arrays.asList(map("预约待接诊", 32), map("治疗进行中", 58), map("疫苗待提醒", 25), map("随访待回访", 8)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
