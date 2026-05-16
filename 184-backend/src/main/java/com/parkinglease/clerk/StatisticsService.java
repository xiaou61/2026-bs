package com.parkinglease.clerk;

import com.parkinglease.mapper.ParkingLotMapper;
import com.parkinglease.mapper.ParkingSpaceMapper;
import com.parkinglease.mapper.TenantProfileMapper;
import com.parkinglease.mapper.LeaseContractMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ParkingLotMapper parkingLotMapper;
    private final ParkingSpaceMapper parkingSpaceMapper;
    private final TenantProfileMapper tenantProfileMapper;
    private final LeaseContractMapper leaseContractMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("lotCount", parkingLotMapper.countAll());
        data.put("spaceCount", parkingSpaceMapper.countAll());
        data.put("tenantCount", tenantProfileMapper.countAll());
        data.put("contractCount", leaseContractMapper.countAll());
        data.put("trend", Arrays.asList(58, 74, 88, 105, 121, 137, 154));
        data.put("pie", Arrays.asList(map("已登记", 31), map("已下单", 44), map("备餐中", 19), map("已关闭", 68)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
