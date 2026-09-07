package com.publicservice.clerk;

import com.publicservice.mapper.ServiceItemMapper;
import com.publicservice.mapper.WindowProfileMapper;
import com.publicservice.mapper.ClerkRosterMapper;
import com.publicservice.mapper.AppointmentBookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ServiceItemMapper serviceItemMapper;
    private final WindowProfileMapper windowProfileMapper;
    private final ClerkRosterMapper clerkRosterMapper;
    private final AppointmentBookingMapper appointmentBookingMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("itemCount", serviceItemMapper.countAll());
        data.put("windowCount", windowProfileMapper.countAll());
        data.put("rosterCount", clerkRosterMapper.countAll());
        data.put("appointmentCount", appointmentBookingMapper.countAll());
        data.put("trend", Arrays.asList(31, 46, 58, 72, 86, 101, 119));
        data.put("pie", Arrays.asList(map("已预约", 36), map("待叫号", 21), map("审核中", 24), map("办理中", 32), map("已评价", 28)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
