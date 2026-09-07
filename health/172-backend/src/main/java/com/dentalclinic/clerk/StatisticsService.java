package com.dentalclinic.clerk;

import com.dentalclinic.mapper.ClinicRoomMapper;
import com.dentalclinic.mapper.DentistProfileMapper;
import com.dentalclinic.mapper.PatientProfileMapper;
import com.dentalclinic.mapper.TreatmentCatalogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ClinicRoomMapper clinicRoomMapper;
    private final DentistProfileMapper dentistProfileMapper;
    private final PatientProfileMapper patientProfileMapper;
    private final TreatmentCatalogMapper treatmentCatalogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("roomCount", clinicRoomMapper.countAll());
        data.put("dentistCount", dentistProfileMapper.countAll());
        data.put("patientCount", patientProfileMapper.countAll());
        data.put("treatmentCount", treatmentCatalogMapper.countAll());
        data.put("trend", Arrays.asList(42, 58, 73, 91, 106, 124, 139));
        data.put("pie", Arrays.asList(map("待就诊", 21), map("治疗中", 33), map("待结算", 18), map("已完成", 49)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
