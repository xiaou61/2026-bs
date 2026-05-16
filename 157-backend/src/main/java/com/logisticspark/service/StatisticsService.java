package com.logisticspark.service;

import com.logisticspark.mapper.CarrierCompanyMapper;
import com.logisticspark.mapper.VehicleProfileMapper;
import com.logisticspark.mapper.DriverProfileMapper;
import com.logisticspark.mapper.GateAppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CarrierCompanyMapper carrierCompanyMapper;
    private final VehicleProfileMapper vehicleProfileMapper;
    private final DriverProfileMapper driverProfileMapper;
    private final GateAppointmentMapper gateAppointmentMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("carrierCount", carrierCompanyMapper.countAll());
        data.put("vehicleCount", vehicleProfileMapper.countAll());
        data.put("driverCount", driverProfileMapper.countAll());
        data.put("appointmentCount", gateAppointmentMapper.countAll());
        data.put("trend", Arrays.asList(120, 146, 163, 188, 201, 234, 260));
        data.put("pie", Arrays.asList(map("预约待入场", 32), map("门岗已核验", 58), map("道口已分配", 25), map("装卸已完成", 8)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
