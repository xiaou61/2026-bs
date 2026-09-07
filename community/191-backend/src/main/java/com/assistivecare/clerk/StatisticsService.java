package com.assistivecare.clerk;

import com.assistivecare.mapper.ServiceCenterMapper;
import com.assistivecare.mapper.ResidentProfileMapper;
import com.assistivecare.mapper.AssistiveDeviceMapper;
import com.assistivecare.mapper.BorrowApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ServiceCenterMapper serviceCenterMapper;
    private final ResidentProfileMapper residentProfileMapper;
    private final AssistiveDeviceMapper assistiveDeviceMapper;
    private final BorrowApplicationMapper borrowApplicationMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("centerCount", serviceCenterMapper.countAll());
        data.put("residentCount", residentProfileMapper.countAll());
        data.put("deviceCount", assistiveDeviceMapper.countAll());
        data.put("borrowCount", borrowApplicationMapper.countAll());
        data.put("trend", Arrays.asList(36, 48, 62, 75, 91, 104, 118));
        data.put("pie", Arrays.asList(map("待审核", 28), map("借用中", 42), map("随访中", 31), map("待回收", 16), map("已闭环", 57)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
