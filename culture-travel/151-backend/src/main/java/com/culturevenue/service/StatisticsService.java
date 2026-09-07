package com.culturevenue.service;

import com.culturevenue.mapper.VenueInfoMapper;
import com.culturevenue.mapper.TicketProductMapper;
import com.culturevenue.mapper.TicketOrderMapper;
import com.culturevenue.mapper.GuideProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final VenueInfoMapper venueInfoMapper;
    private final TicketProductMapper ticketProductMapper;
    private final TicketOrderMapper ticketOrderMapper;
    private final GuideProfileMapper guideProfileMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("venueCount", venueInfoMapper.countAll());
        data.put("ticketCount", ticketProductMapper.countAll());
        data.put("ticket-orderCount", ticketOrderMapper.countAll());
        data.put("guideCount", guideProfileMapper.countAll());
        data.put("trend", Arrays.asList(120, 146, 163, 188, 201, 234, 260));
        data.put("pie", Arrays.asList(map("预约待确认", 32), map("已核销入馆", 58), map("讲解服务中", 25), map("客流预警", 8)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
