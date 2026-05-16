package com.visitoraccess.clerk;

import com.visitoraccess.mapper.CompanyZoneMapper;
import com.visitoraccess.mapper.HostEmployeeMapper;
import com.visitoraccess.mapper.VisitorProfileMapper;
import com.visitoraccess.mapper.VisitAppointmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CompanyZoneMapper companyZoneMapper;
    private final HostEmployeeMapper hostEmployeeMapper;
    private final VisitorProfileMapper visitorProfileMapper;
    private final VisitAppointmentMapper visitAppointmentMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("zoneCount", companyZoneMapper.countAll());
        data.put("hostCount", hostEmployeeMapper.countAll());
        data.put("visitorCount", visitorProfileMapper.countAll());
        data.put("appointmentCount", visitAppointmentMapper.countAll());
        data.put("trend", Arrays.asList(96, 118, 135, 165, 182, 207, 229));
        data.put("pie", Arrays.asList(map("待审批", 34), map("已预约", 57), map("通行中", 26), map("已离园", 18)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
